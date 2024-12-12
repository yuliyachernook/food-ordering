package by.ita.chernook.mapper;

import by.ita.chernook.dto.OrderWebDto;
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

    public OrderWebDto toWebDTO(Order order) {
        return OrderWebDto.builder()
                .uuid(order.getUuid())
                .orderItems(order.getOrderItems() != null ?
                        order.getOrderItems().stream()
                                .map(orderItemMapper::toWebDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(order.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toWebDTO(order.getDeliveryAddress()) : null)
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .comment(order.getComment())
                .build();
    }

    public Order toEntity(OrderWebDto orderWebDto) {
        return Order.builder()
                .uuid(orderWebDto.getUuid())
                .orderItems(orderWebDto.getOrderItems() != null ?
                        orderWebDto.getOrderItems().stream()
                                .map(orderItemMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(orderWebDto.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toEntity(orderWebDto.getDeliveryAddress()) : null)
                .totalPrice(orderWebDto.getTotalPrice())
                .orderStatus(orderWebDto.getOrderStatus())
                .comment(orderWebDto.getComment())
                .build();
    }
}
