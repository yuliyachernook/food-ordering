package by.ita.chernook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "cartUuid", nullable = false)
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "productUuid", nullable = false)
    private Product product;
    private Integer quantity;
}
