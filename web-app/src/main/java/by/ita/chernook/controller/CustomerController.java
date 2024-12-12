package by.ita.chernook.controller;

import by.ita.chernook.dto.CouponWebDto;
import by.ita.chernook.dto.CustomerWebDto;
import by.ita.chernook.dto.DeliveryAddressWebDto;
import by.ita.chernook.dto.OrderWebDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.model.CustomUserDetails;
import by.ita.chernook.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CouponService couponService;

    private final DeliveryAddressService deliveryAddressService;

    private final DeliveryAddressMapper deliveryAddressMapper;
    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;
    private final CouponMapper couponMapper;

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        CustomerWebDto customer = customerMapper.toWebDTO(userService.findCustomerById(customUserDetails.getCustomerUuid()));
        List<OrderWebDto> orders = orderService.readAllOrdersByCustomerUuid(customer.getUuid()).stream()
                .map(orderMapper::toWebDTO)
                .collect(Collectors.toList());
        List<CouponWebDto> coupons = couponService.readAllGlobalCoupons().stream()
                .map(couponMapper::toWebDTO)
                .collect(Collectors.toList());
        model.addAttribute("coupons", coupons);
        model.addAttribute("customer", customer);
        model.addAttribute("address", new DeliveryAddressWebDto());
        model.addAttribute("orders", orders);
        return "profile";
    }

    @PostMapping("/add/delivery/address")
    public String addDeliveryAddress(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute DeliveryAddressWebDto deliveryAddressWebDto) {
        CustomerWebDto customer = customerMapper.toWebDTO(customerService.addDeliveryAddress(customUserDetails.getCustomerUuid(), deliveryAddressMapper.toEntity(deliveryAddressWebDto)));
        return "redirect:/customer/profile";
    }

    @PostMapping("/delete/delivery/address")
    public String deleteDeliveryAddress(String deliveryAddressId) {
        deliveryAddressService.deleteDeliveryAddress(deliveryAddressId);
        return "redirect:/customer/profile";
    }

    @GetMapping("/all")
    public String mainPage(Model model) {
        List<CustomerWebDto> products = customerService.readAllCustomers().stream()
                .map(customerMapper::toWebDTO)
                .collect(Collectors.toList());
        model.addAttribute("customers", products);
        return "customers";
    }
}