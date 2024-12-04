package by.ita.chernook.dto;

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
public class OrderDto {

    private UUID uuid;
    private CustomerDto customer;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private DeliveryAddressDto deliveryAddress;
    private double totalPrice;
    private String orderStatus;
    private ZonedDateTime creationDateTime;
    private String comment;
}