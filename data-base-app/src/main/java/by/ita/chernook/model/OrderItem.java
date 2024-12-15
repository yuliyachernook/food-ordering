package by.ita.chernook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "orderUuid")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "productUuid", nullable = false)
    private Product product;
    private BigDecimal price;
    private Integer quantity;
}
