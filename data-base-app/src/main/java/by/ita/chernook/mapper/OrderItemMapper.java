package by.ita.chernook.mapper;

import by.ita.chernook.dto.OrderItemDto;
import by.ita.chernook.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderItemMapper {

    public OrderItemDto toDTO(OrderItem orderItem) {
        return OrderItemDto.builder()
                .uuid(orderItem.getUuid())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItem toEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .uuid(orderItemDto.getUuid())
                .quantity(orderItemDto.getQuantity())
                .build();
    }
}
