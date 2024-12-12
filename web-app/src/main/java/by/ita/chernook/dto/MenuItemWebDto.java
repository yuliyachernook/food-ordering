package by.ita.chernook.dto;

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
public class MenuItemWebDto {

    private UUID uuid;
    private String itemName;
    private double price;
    private String description;

    private Boolean isEdited;
    private ZonedDateTime creationDateTime;
}