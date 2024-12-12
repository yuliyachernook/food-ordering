package by.ita.chernook.mapper;

import by.ita.chernook.dto.CartWebDto;
import by.ita.chernook.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartMapper {

    public CartWebDto toWebDTO(Cart cart) {
        return CartWebDto.builder()
                .uuid(cart.getUuid())

                .build();
    }

    public Cart toEntity(CartWebDto cartWebDto) {
        return Cart.builder()
                .uuid(cartWebDto.getUuid())

                .build();
    }

}
