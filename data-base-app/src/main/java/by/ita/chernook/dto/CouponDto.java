package by.ita.chernook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CouponDto {

    private UUID uuid;
    private String name;
    private String description;
    private String code;
    private Double discountPercentage;
    private Double discountAmount;
}
