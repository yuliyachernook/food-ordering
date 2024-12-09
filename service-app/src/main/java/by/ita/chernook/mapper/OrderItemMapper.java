package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.OrderItemDatabaseDto;
import by.ita.chernook.dto.to_web.OrderItemWebDto;
import by.ita.chernook.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderItemMapper {

    private final ProductMapper productMapper;

    public OrderItemDatabaseDto toDatabaseDTO(OrderItem orderItem) {
        return OrderItemDatabaseDto.builder()
                .uuid(orderItem.getUuid())
                .product(productMapper.toDatabaseDTO(orderItem.getProduct()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItemWebDto toWebDTO(OrderItem orderItem) {
        return OrderItemWebDto.builder()
                .uuid(orderItem.getUuid())
                .product(productMapper.toWebDTO(orderItem.getProduct()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItem toEntity(OrderItemDatabaseDto orderItemDatabaseDto) {
        return OrderItem.builder()
                .uuid(orderItemDatabaseDto.getUuid())
                .product(productMapper.toEntity(orderItemDatabaseDto.getProduct()))
                .price(orderItemDatabaseDto.getPrice())
                .quantity(orderItemDatabaseDto.getQuantity())
                .build();
    }

    public OrderItem toEntity(OrderItemWebDto orderItemWebDto) {
        return OrderItem.builder()
                .uuid(orderItemWebDto.getUuid())
                .product(productMapper.toEntity(orderItemWebDto.getProduct()))
                .price(orderItemWebDto.getPrice())
                .quantity(orderItemWebDto.getQuantity())
                .build();
    }
}
