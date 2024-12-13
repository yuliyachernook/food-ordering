package by.ita.chernook.controller;

import by.ita.chernook.dto.OrderWebDto;
import by.ita.chernook.dto.enums.CouponTypeEnum;
import by.ita.chernook.mapper.*;
import by.ita.chernook.model.*;
import by.ita.chernook.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MainService mainService;

    private final CartService cartService;
    private final UserService userService;
    private final CouponService couponService;


    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;

    @GetMapping
    public String viewOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            Model model, HttpSession session) {

        if (customUserDetails == null) {
            return "redirect:login/";
        }
        Customer customer = userService.findCustomerById(customUserDetails.getCustomerUuid());
        Order order = orderService.buildOrderForCustomer(customUserDetails.getCustomerUuid());

        model.addAttribute("customer", customerMapper.toWebDTO(customer));
        model.addAttribute("order", orderMapper.toWebDTO(order));

        Double discountedTotalPrice = (Double) session.getAttribute("discountedTotalPrice");
        if (discountedTotalPrice != null) {
            model.addAttribute("discountedTotalPrice", discountedTotalPrice);
        }
        String errorMessage = (String) session.getAttribute("errorMessage");
        String successMessage = (String) session.getAttribute("successMessage");

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        return "order";
    }

    @PostMapping("/confirm")
    public String confirmOrder( @AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute OrderWebDto orderWebDto, Model model, HttpSession httpSession) {
        String code = (String) httpSession.getAttribute("coupon");
        Double discountedTotalPrice = (Double) httpSession.getAttribute("discountedTotalPrice");

        if (code != null) {
            Coupon appliedCoupon = couponService.applyCoupon(code);
            if (appliedCoupon == null) {
                httpSession.removeAttribute("discountedTotalPrice");
                httpSession.removeAttribute("coupon");
                httpSession.setAttribute("errorMessage", "Купон недоступен или исчерпан.");
                return "redirect:/order";
            }
        }

        orderService.createOrderForCustomer(orderMapper.toEntity(orderWebDto), customUserDetails.getCustomerUuid(), discountedTotalPrice);
        cartService.cleanCart(mainService.getCartUuidFromSession(httpSession));
        httpSession.removeAttribute("discountedTotalPrice");
        httpSession.removeAttribute("coupon");

        return "redirect:/order/success";
    }

    @PostMapping("/apply/coupon")
    public String applyPromoCode(String code, String totalPrice, Model model, HttpSession session) {

        Coupon coupon = couponService.findCouponByCode(code);

        if (coupon == null || coupon.getAvailableUses() == 0) {
            session.removeAttribute("coupon");
            session.removeAttribute("discountedTotalPrice");
            session.setAttribute("errorMessage", "Купон не найден или недействителен.");
            return "redirect:/order";
        }

        double newTotalPrice = Double.parseDouble(totalPrice);

        if (coupon.getCouponType().equals(CouponTypeEnum.FIXED)) {
            newTotalPrice = newTotalPrice - coupon.getDiscount();
        } else if (coupon.getCouponType().equals(CouponTypeEnum.PERCENT)) {
            newTotalPrice = newTotalPrice - (newTotalPrice * coupon.getDiscount() / 100);
        }

        if (newTotalPrice < 0) newTotalPrice = 0;

        session.setAttribute("coupon", code);
        session.setAttribute("successMessage", "Промокод успешно применен :)");
        session.setAttribute("discountedTotalPrice", newTotalPrice);
        return "redirect:/order";
    }

    @GetMapping("success")
    public String showSuccessPage() {
        return "orderSuccess";
    }

}
