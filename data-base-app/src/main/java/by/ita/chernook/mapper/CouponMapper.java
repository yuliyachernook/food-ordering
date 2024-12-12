package by.ita.chernook.mapper;

import by.ita.chernook.dto.CouponDto;
import by.ita.chernook.model.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponMapper {

    public CouponDto toDTO(Coupon coupon) {
        return CouponDto.builder()
                .uuid(coupon.getUuid())
                .name(coupon.getName())
                .description(coupon.getDescription())
                .code(coupon.getCode())
                .couponType(coupon.getCouponType())
                .discount(coupon.getDiscount())
                .availableUses(coupon.getAvailableUses())
                .build();
    }

    public Coupon toEntity(CouponDto couponDto) {
        return Coupon.builder()
                .uuid(couponDto.getUuid())
                .name(couponDto.getName())
                .description(couponDto.getDescription())
                .code(couponDto.getCode())
                .couponType(couponDto.getCouponType())
                .discount(couponDto.getDiscount())
                .availableUses(couponDto.getAvailableUses())
                .build();
    }
}
