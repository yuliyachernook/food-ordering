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
                .couponType(coupon.getCouponType())
                .discount(coupon.getDiscount())
                .availableUses(coupon.getAvailableUses())
                .build();
    }

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

    public Coupon toEntity(CouponDatabaseDto couponDatabaseDto) {
        return Coupon.builder()
                .uuid(couponDatabaseDto.getUuid())
                .name(couponDatabaseDto.getName())
                .description(couponDatabaseDto.getDescription())
                .code(couponDatabaseDto.getCode())
                .couponType(couponDatabaseDto.getCouponType())
                .discount(couponDatabaseDto.getDiscount())
                .availableUses(couponDatabaseDto.getAvailableUses())
                .build();
    }
}
