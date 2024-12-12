package by.ita.chernook.mapper;

import by.ita.chernook.dto.CouponWebDto;
import by.ita.chernook.model.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponMapper {

    public CouponWebDto toWebDTO(Coupon coupon) {
        return CouponWebDto.builder()
                .uuid(coupon.getUuid())
                .name(coupon.getName())
                .description(coupon.getDescription())
                .code(coupon.getCode())
                .couponType(coupon.getCouponType())
                .discount(coupon.getDiscount())
                .availableUses(coupon.getAvailableUses())
                .build();
    }

    public Coupon toEntity(CouponWebDto couponWebDto) {
        return Coupon.builder()
                .uuid(couponWebDto.getUuid())
                .name(couponWebDto.getName())
                .description(couponWebDto.getDescription())
                .code(couponWebDto.getCode())
                .couponType(couponWebDto.getCouponType())
                .discount(couponWebDto.getDiscount())
                .availableUses(couponWebDto.getAvailableUses())
                .build();
    }
}
