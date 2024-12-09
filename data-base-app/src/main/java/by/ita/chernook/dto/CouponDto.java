package by.ita.chernook.dto;

import by.ita.chernook.model.Customer;
import by.ita.chernook.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
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
    private LocalDate expirationDate;
    private Boolean isGlobal;
    private List<CustomerDto> customers;
}
