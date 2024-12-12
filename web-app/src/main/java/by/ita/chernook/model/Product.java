package by.ita.chernook.model;

import by.ita.chernook.dto.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {

    private UUID uuid;
    private String itemName;
    private double price;
    private String description;
    private CategoryEnum category;
    private int discountPercentage;
    private ZonedDateTime creationDateTime;

    private byte[] image;
}