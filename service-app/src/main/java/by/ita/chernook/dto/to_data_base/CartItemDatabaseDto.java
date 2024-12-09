package by.ita.chernook.dto.to_data_base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartItemDatabaseDto {

    private UUID uuid;
    private CartDatabaseDto cart;
    private ProductDatabaseDto product;
    private Integer quantity;
}
