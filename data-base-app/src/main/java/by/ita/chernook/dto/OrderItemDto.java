package by.ita.chernook.dto;

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
public class OrderItemDto {

    private UUID uuid;
    private OrderDto order;
    private ProductDto product;
    private BigDecimal price;
    private Integer quantity;
}
