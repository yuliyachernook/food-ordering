package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.OrderDatabaseDto;
import by.ita.chernook.dto.to_web.OrderWebDto;
import by.ita.chernook.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;
    private final CustomerMapper customerMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    public OrderDatabaseDto toDatabaseDTO(Order order) {
        return OrderDatabaseDto.builder()
                .uuid(order.getUuid())
                .orderItems(order.getOrderItems() != null ?
                        order.getOrderItems().stream()
                                .map(orderItemMapper::toDatabaseDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(order.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toDatabaseDTO(order.getDeliveryAddress()) : null)
                .totalPrice(order.getTotalPrice())
                .customer(order.getCustomer() != null ? customerMapper.toDatabaseDTO(order.getCustomer()) : null)
                .orderStatus(order.getOrderStatus())
                .comment(order.getComment())
                .creationDateTime(order.getCreationDateTime())
                .build();
    }

    public OrderWebDto toWebDTO(Order order) {
        return OrderWebDto.builder()
                .uuid(order.getUuid())
                .orderItems(order.getOrderItems() != null ?
                        order.getOrderItems().stream()
                                .map(orderItemMapper::toWebDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(order.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toWebDTO(order.getDeliveryAddress()) : null)
                .customer(order.getCustomer() != null ? customerMapper.toWebDTO(order.getCustomer()) : null)
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .comment(order.getComment())
                .creationDateTime(order.getCreationDateTime())
                .build();
    }

    public Order toEntity(OrderDatabaseDto orderDatabaseDto) {
        return Order.builder()
                .uuid(orderDatabaseDto.getUuid())
                .orderItems(orderDatabaseDto.getOrderItems() != null ?
                        orderDatabaseDto.getOrderItems().stream()
                                .map(orderItemMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .deliveryAddress(orderDatabaseDto.getDeliveryAddress() != null ?
                        deliveryAddressMapper.toEntity(orderDatabaseDto.getDeliveryAddress()) : null)
                .customer(orderDatabaseDto.getCustomer() != null ? customerMapper.toEntity(orderDatabaseDto.getCustomer()) : null)
                .totalPrice(orderDatabaseDto.getTotalPrice())
                .orderStatus(orderDatabaseDto.getOrderStatus())
                .comment(orderDatabaseDto.getComment())
                .creationDateTime(orderDatabaseDto.getCreationDateTime())
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
                .customer(orderWebDto.getCustomer() != null ? customerMapper.toEntity(orderWebDto.getCustomer()) : null)
                .totalPrice(orderWebDto.getTotalPrice())
                .orderStatus(orderWebDto.getOrderStatus())
                .comment(orderWebDto.getComment())
                .creationDateTime(orderWebDto.getCreationDateTime())
                .build();
    }
}
