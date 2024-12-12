package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.CouponWebDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/business/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @PostMapping("/create")
    public CouponWebDto createGlobal(@RequestBody CouponWebDto couponWebDto) {
        Coupon coupon = couponMapper.toEntity(couponWebDto);
        Coupon insertedCoupon = couponService.createCoupon(coupon);
        return couponMapper.toWebDTO(insertedCoupon);
    }

    @PostMapping("/apply")
    public CouponWebDto apply(@RequestParam String coupon) {
        Coupon updatedCoupon = couponService.applyCoupon(coupon);
        return couponMapper.toWebDTO(updatedCoupon);
    }

    @GetMapping("/read/code")
    public CouponWebDto read(@RequestParam String code) {
        Coupon coupon = couponService.findCouponByCode(code);
        return couponMapper.toWebDTO(coupon);
    }

    @GetMapping("/read/all")
    public List<CouponWebDto> readAll() {
        return couponService.findAllGlobalCoupons()
                .stream()
                .map(couponMapper::toWebDTO)
                .collect(Collectors.toList());
    }
}

