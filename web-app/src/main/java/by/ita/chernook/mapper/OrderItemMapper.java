package by.ita.chernook.mapper;

import by.ita.chernook.dto.OrderItemWebDto;
import by.ita.chernook.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderItemMapper {

    private final ProductMapper productMapper;

    public OrderItemWebDto toWebDTO(OrderItem orderItem) {
        return OrderItemWebDto.builder()
                .uuid(orderItem.getUuid())
                .product(productMapper.toWebDTO(orderItem.getProduct()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
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
