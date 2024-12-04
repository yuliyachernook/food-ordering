package by.ita.chernook.mapper;

import by.ita.chernook.dto.CartItemDto;
import by.ita.chernook.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CartItemMapper {

    public CartItemDto toDTO(CartItem cartItem) {
        return CartItemDto.builder()
                .uuid(cartItem.getUuid())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .uuid(cartItemDto.getUuid())
                .quantity(cartItemDto.getQuantity())
                .build();
    }
}
