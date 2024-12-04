package by.ita.chernook.mapper;

import by.ita.chernook.dto.OrderDto;
import by.ita.chernook.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    public OrderDto toDTO(Order order) {
        return OrderDto.builder()
                .uuid(order.getUuid())
                .orderItems(order.getOrderItems() != null ?
                        order.getOrderItems().stream()
                                .map(orderItemMapper::toDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(order.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toDTO(order.getDeliveryAddress()) : null)
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .comment(order.getComment())
                .build();
    }

    public Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .uuid(orderDto.getUuid())
                .orderItems(orderDto.getOrderItems() != null ?
                        orderDto.getOrderItems().stream()
                                .map(orderItemMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(orderDto.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toEntity(orderDto.getDeliveryAddress()) : null)
                .totalPrice(orderDto.getTotalPrice())
                .orderStatus(orderDto.getOrderStatus())
                .comment(orderDto.getComment())
                .build();
    }
}
