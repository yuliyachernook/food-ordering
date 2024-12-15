package by.ita.chernook.model;

import by.ita.chernook.dto.enums.OrderStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "customerUuid")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "deliveryAddressUuid")
    private DeliveryAddress deliveryAddress;

    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
    private String comment;
    private ZonedDateTime creationDateTime;
}