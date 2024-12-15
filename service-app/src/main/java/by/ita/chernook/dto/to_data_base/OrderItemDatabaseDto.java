package by.ita.chernook.dto.to_data_base;

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
public class OrderItemDatabaseDto {

    private UUID uuid;
    private OrderDatabaseDto order;
    private ProductDatabaseDto product;
    private BigDecimal price;
    private Integer quantity;
}
