package by.ita.chernook.dto.to_web;

import by.ita.chernook.dto.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal totalPrice;
    private OrderStatusEnum orderStatus;
    private ZonedDateTime creationDateTime;
    private String comment;
}