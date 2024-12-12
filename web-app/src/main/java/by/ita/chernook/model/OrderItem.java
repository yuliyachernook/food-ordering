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
public class OrderItem {

    private UUID uuid;
    private Order order;
    private Product product;
    private Double price;
    private Integer quantity;
}