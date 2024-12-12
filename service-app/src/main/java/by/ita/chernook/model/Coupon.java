package by.ita.chernook.model;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Coupon {

    private UUID uuid;
    private String name;
    private String description;
    private String code;
    private CouponTypeEnum couponType;
    private int discount;
    private int availableUses;
}
