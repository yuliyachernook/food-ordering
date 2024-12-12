package by.ita.chernook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartItem {

    private UUID uuid;
    private Cart cart;
    private Product product;
    private Integer quantity;
}
