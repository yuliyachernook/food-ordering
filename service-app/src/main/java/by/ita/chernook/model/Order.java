package by.ita.chernook.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Order {

    private UUID uuid;
    @ToString.Exclude
    private Customer customer;
    private List<OrderItem> orderItems = new ArrayList<>();

    @ToString.Exclude
    private DeliveryAddress deliveryAddress;

    private double totalPrice;
    private String orderStatus;
    private String comment;
    private ZonedDateTime creationDateTime;
}