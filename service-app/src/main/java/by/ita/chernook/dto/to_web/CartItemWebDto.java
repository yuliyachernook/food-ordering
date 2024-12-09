package by.ita.chernook.dto.to_web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartItemWebDto {

    private UUID uuid;
    private CartWebDto cart;
    private ProductWebDto product;
    private Integer quantity;
    //TODO price
}
