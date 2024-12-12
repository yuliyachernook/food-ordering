package by.ita.chernook.model;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false, unique = true)
    private String code;
    @Enumerated(EnumType.STRING)
    private CouponTypeEnum couponType;
    private int discount;
    private int availableUses;
}
