package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.CouponWebDto;
import by.ita.chernook.dto.to_web.CustomerWebDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.model.Customer;
import by.ita.chernook.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/business/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;
    private final CustomerMapper customerMapper;


    @PostMapping("/create")
    public CouponWebDto create(@RequestBody CouponWebDto couponWebDto) {
        Coupon coupon = couponMapper.toEntity(couponWebDto);
        Coupon insertedCoupon = couponService.createCoupon(coupon);
        return couponMapper.toWebDTO(insertedCoupon);
    }

    @PostMapping("/add/customer")
    public CustomerWebDto addCouponToCustomer(@RequestParam UUID id, @RequestBody CouponWebDto couponWebDto) {
        Coupon coupon = couponMapper.toEntity(couponWebDto);
        Customer insertedCoupon = couponService.addCouponToCustomer(id, coupon);
        return customerMapper.toWebDTO(insertedCoupon);
    }
}

