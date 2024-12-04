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
public class CartItemDto {

    private UUID uuid;
    private CartDto cart;
    private ProductDto product;
    private Integer quantity;
}
