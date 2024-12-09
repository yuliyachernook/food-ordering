package by.ita.chernook.mapper;

import by.ita.chernook.dto.CartItemDto;
import by.ita.chernook.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CartItemMapper {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    public CartItemDto toDTO(CartItem cartItem) {
        return CartItemDto.builder()
                .uuid(cartItem.getUuid())
                .cart(cartMapper.toDTO(cartItem.getCart()))
                .product(productMapper.toDTO(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .uuid(cartItemDto.getUuid())
                .cart(cartMapper.toEntity(cartItemDto.getCart()))
                .product(productMapper.toEntity(cartItemDto.getProduct()))
                .quantity(cartItemDto.getQuantity())
                .build();
    }
}
