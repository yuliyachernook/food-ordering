package by.ita.chernook.controller;

import by.ita.chernook.dto.CouponDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @PostMapping("/create")
    public CouponDto create(@RequestBody CouponDto couponDto) {
        Coupon insertedCoupon = couponService.createCoupon(couponMapper.toEntity(couponDto));
        return couponMapper.toDTO(insertedCoupon);
    }

    @PutMapping("/update")
    public CouponDto update(@RequestBody CouponDto couponDto) {
        Coupon updatedCoupon = couponService.updateCoupon(couponMapper.toEntity(couponDto));
        return couponMapper.toDTO(updatedCoupon);
    }

    @GetMapping("/read")
    public CouponDto read(@RequestParam UUID uuid) {
        Coupon coupon = couponService.findCouponById(uuid);
        return couponMapper.toDTO(coupon);
    }

    @GetMapping("/read/code")
    public CouponDto read(@RequestParam String code) {
        Coupon coupon = couponService.findCouponByCode(code);
        return couponMapper.toDTO(coupon);
    }

    @GetMapping("/read/all")
    public List<CouponDto> readAll() {
        return couponService.findAll()
                .stream()
                .map(couponMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public CouponDto delete(@RequestParam UUID uuid) {
        Coupon deletedCoupon = couponService.deleteCoupon(uuid);
        return couponMapper.toDTO(deletedCoupon);
    }
}

