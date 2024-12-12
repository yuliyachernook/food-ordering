package by.ita.chernook.dto;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CouponWebDto {

    private UUID uuid;
    private String name;
    private String description;
    private String code;
    private CouponTypeEnum couponType;
    private int discount;
    private int availableUses;
}