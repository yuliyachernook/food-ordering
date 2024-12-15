package by.ita.chernook.dto.to_web;

import by.ita.chernook.dto.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductWebDto {

    private UUID uuid;
    private String itemName;
    private BigDecimal price;
    private String description;
    private CategoryEnum category;
    private int discountPercentage;

    private byte[] image;
}