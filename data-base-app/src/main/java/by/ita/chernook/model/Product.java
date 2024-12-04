package by.ita.chernook.model;

import by.ita.chernook.dto.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String itemName;
    private double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private int discountPercentage;
    private ZonedDateTime creationDateTime;

    //TODO: image
}