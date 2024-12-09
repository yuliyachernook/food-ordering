package by.ita.chernook.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
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
    private Double discountPercentage;
    private Double discountAmount;
    private LocalDate expirationDate;
    private Boolean isGlobal;
}
