package by.ita.chernook.dto.to_web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderWebDto {

    private UUID uuid;
    private CustomerWebDto customer;
    private List<OrderItemWebDto> orderItems;
    private DeliveryAddressWebDto deliveryAddress;
    private double totalPrice;
    private String orderStatus;
    private ZonedDateTime creationDateTime;
    private String comment;
}