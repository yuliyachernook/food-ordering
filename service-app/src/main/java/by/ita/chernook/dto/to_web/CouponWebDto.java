package by.ita.chernook.dto.to_web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
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
    private Double discountPercentage;
    private Double discountAmount;
    private LocalDate expirationDate;
    private Boolean isGlobal;
}