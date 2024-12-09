package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.CouponDatabaseDto;
import by.ita.chernook.dto.to_web.CouponWebDto;
import by.ita.chernook.model.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CouponMapper {

    public CouponDatabaseDto toDatabaseDTO(Coupon coupon) {
        return CouponDatabaseDto.builder()
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

    public CouponWebDto toWebDTO(Coupon coupon) {
        return CouponWebDto.builder()
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

    public Coupon toEntity(CouponWebDto couponWebDto) {
        return Coupon.builder()
                .uuid(couponWebDto.getUuid())
                .name(couponWebDto.getName())
                .description(couponWebDto.getDescription())
                .code(couponWebDto.getCode())
                .discountPercentage(couponWebDto.getDiscountPercentage())
                .discountAmount(couponWebDto.getDiscountAmount())
                .expirationDate(couponWebDto.getExpirationDate())
                .isGlobal(couponWebDto.getIsGlobal())
                .build();
    }

    public Coupon toEntity(CouponDatabaseDto couponDatabaseDto) {
        return Coupon.builder()
                .uuid(couponDatabaseDto.getUuid())
                .name(couponDatabaseDto.getName())
                .description(couponDatabaseDto.getDescription())
                .code(couponDatabaseDto.getCode())
                .discountPercentage(couponDatabaseDto.getDiscountPercentage())
                .discountAmount(couponDatabaseDto.getDiscountAmount())
                .expirationDate(couponDatabaseDto.getExpirationDate())
                .isGlobal(couponDatabaseDto.getIsGlobal())
                .build();
    }
}
