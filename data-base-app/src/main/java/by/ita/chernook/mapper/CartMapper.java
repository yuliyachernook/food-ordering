package by.ita.chernook.mapper;

import by.ita.chernook.dto.CartDto;
import by.ita.chernook.dto.CustomerDto;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CartMapper {

    private final CartItemMapper cartItemMapper;

    public CartDto toDTO(Cart cart) {
        return CartDto.builder()
                .uuid(cart.getUuid())
                .cartItems(cart.getCartItems() != null ?
                        cart.getCartItems().stream()
                                .map(cartItemMapper::toDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    public Cart toEntity(CartDto cartDto) {
        return Cart.builder()
                .uuid(cartDto.getUuid())
                .cartItems(cartDto.getCartItems() != null ?
                        cartDto.getCartItems().stream()
                                .map(cartItemMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

}
