package by.ita.chernook.mapper;

import by.ita.chernook.dto.CartDto;
import by.ita.chernook.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartMapper {

    public CartDto toDTO(Cart cart) {
        return CartDto.builder()
                .uuid(cart.getUuid())
                .build();
    }

    public Cart toEntity(CartDto cartDto) {
        return Cart.builder()
                .uuid(cartDto.getUuid())
                .build();
    }

}
