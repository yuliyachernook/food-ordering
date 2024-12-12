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
                            @RequestParam(required = false) Double totalPrice,
                            @RequestParam(required = false) String applyCoupon,
                            @RequestParam(required = false) boolean invalidCoupon,
                            HttpSession session,
                            Model model) {

        if (customUserDetails == null) {
            return "redirect:login/";
        }
        Customer customer = userService.findCustomerById(customUserDetails.getCustomerUuid());
        Order order = orderService.buildOrderForCustomer(customUserDetails.getCustomerUuid());

        model.addAttribute("customer", customerMapper.toWebDTO(customer));
        model.addAttribute("order", orderMapper.toWebDTO(order));

        if (totalPrice != null) {
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("totalPrice", order.getTotalPrice());
        }
        if (applyCoupon!= null) {
            if (!applyCoupon.isEmpty()) {
                session.setAttribute("coupon", applyCoupon);
                model.addAttribute("successMessage", "Промокод успешно применен :)");
            } else {
                model.addAttribute("successMessage", "Промокод не найден :(");
            }
        }
        return "order";
    }

    @PostMapping("/confirm")
    public String confirmOrder( @AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute OrderWebDto orderWebDto, Model model, HttpSession httpSession) {
        String code = (String) httpSession.getAttribute("coupon");
        Boolean isInvalid = (Boolean) httpSession.getAttribute("invalidCoupon");
        if (code != null) {
            couponService.applyCoupon(code);
            orderService.createOrderForCustomer(orderMapper.toEntity(orderWebDto), customUserDetails.getCustomerUuid(), (String) httpSession.getAttribute("coupon"));
            cartService.cleanCart(mainService.getCartUuidFromSession(httpSession));
            httpSession.removeAttribute("coupon");
            return "redirect:/order/success";
        } else {
            orderService.createOrderForCustomer(orderMapper.toEntity(orderWebDto), customUserDetails.getCustomerUuid(), null);
            cartService.cleanCart(mainService.getCartUuidFromSession(httpSession));
            return "redirect:/order/success";
        }
    }

    @PostMapping("/apply/coupon")
    public String applyPromoCode(String code, String totalPrice, Model model) {

        Coupon coupon = couponService.findCouponByCode(code);
        if (coupon != null) {
            double newTotalPrice = Double.parseDouble(totalPrice);

            if (coupon.getCouponType().equals(CouponTypeEnum.FIXED)) {
                newTotalPrice = newTotalPrice - coupon.getDiscount();
            } else if (coupon.getCouponType().equals(CouponTypeEnum.PERCENT)) {
                newTotalPrice = newTotalPrice - (newTotalPrice * coupon.getDiscount() / 100);
            }

            if (newTotalPrice < 0) newTotalPrice = 0;

            model.addAttribute("totalPrice", newTotalPrice);
            model.addAttribute("applyCoupon", code);
            return "redirect:/order?totalPrice=" + model.getAttribute("totalPrice") + "&applyCoupon=" +model.getAttribute("applyCoupon");
        } else {
            return "redirect:/order?applyCoupon=" +model.getAttribute("applyCoupon");
        }
    }

    @GetMapping("success")
    public String showSuccessPage() {
        return "orderSuccess";
    }

}
