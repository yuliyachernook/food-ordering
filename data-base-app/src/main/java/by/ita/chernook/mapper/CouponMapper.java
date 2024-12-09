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
                .discountPercentage(coupon.getDiscountPercentage())
                .discountAmount(coupon.getDiscountAmount())
                .expirationDate(coupon.getExpirationDate())
                .isGlobal(coupon.getIsGlobal())
                .build();
    }

    public Coupon toEntity(CouponDto couponDto) {
        return Coupon.builder()
                .uuid(couponDto.getUuid())
                .name(couponDto.getName())
                .description(couponDto.getDescription())
                .code(couponDto.getCode())
                .discountPercentage(couponDto.getDiscountPercentage())
                .discountAmount(couponDto.getDiscountAmount())
                .expirationDate(couponDto.getExpirationDate())
                .isGlobal(couponDto.getIsGlobal())
                .build();
    }
}
