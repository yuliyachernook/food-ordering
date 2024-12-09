package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_web.CartWebDto;
import by.ita.chernook.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CartMapper {

    public CartDatabaseDto toDatabaseDTO(Cart cart) {
        return CartDatabaseDto.builder()
                .uuid(cart.getUuid())
                .build();
    }

    public CartWebDto toWebDTO(Cart cart) {
        return CartWebDto.builder()
                .uuid(cart.getUuid())
                 .build();
    }

    public Cart toEntity(CartDatabaseDto cartDatabaseDto) {
        return Cart.builder()
                .uuid(cartDatabaseDto.getUuid())
               .build();
    }

    public Cart toEntity(CartWebDto cartWebDto) {
        return Cart.builder()
                .uuid(cartWebDto.getUuid())
               .build();
    }

}
