package by.ita.chernook.controller;

import by.ita.chernook.dto.CouponDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/database/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @PostMapping("/create")
    public CouponDto create(@RequestBody CouponDto couponDto) {
        Coupon coupon = couponMapper.toEntity(couponDto);
        Coupon insertedCoupon = couponService.createCoupon(coupon);
        return couponMapper.toDTO(insertedCoupon);
    }
}

