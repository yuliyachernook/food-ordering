package by.ita.chernook.mapper;

import by.ita.chernook.dto.OrderItemDto;
import by.ita.chernook.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderItemMapper {

    private final ProductMapper productMapper;

    public OrderItemDto toDTO(OrderItem orderItem) {
        return OrderItemDto.builder()
                .uuid(orderItem.getUuid())
                .product(productMapper.toDTO(orderItem.getProduct()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItem toEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .uuid(orderItemDto.getUuid())
                .product(productMapper.toEntity(orderItemDto.getProduct()))
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .build();
    }
}
