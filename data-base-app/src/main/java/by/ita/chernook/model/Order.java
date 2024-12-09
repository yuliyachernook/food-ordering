package by.ita.chernook.model;

import lombok.*;

import javax.persistence.*;
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
    @JoinColumn(name = "customerUuid", nullable = false)
    @ToString.Exclude
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "deliveryAddressUuid")
    private DeliveryAddress deliveryAddress;

    private double totalPrice;
    private String orderStatus;
    private String comment;
    private ZonedDateTime creationDateTime;
}