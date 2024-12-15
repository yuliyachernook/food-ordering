package by.ita.chernook.dto.to_web;

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
public class OrderItemWebDto {

    private UUID uuid;
    private OrderWebDto order;
    private ProductWebDto product;
    private BigDecimal price;
    private Integer quantity;
}
