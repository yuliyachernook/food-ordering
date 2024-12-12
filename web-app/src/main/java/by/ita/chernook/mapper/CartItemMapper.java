package by.ita.chernook.mapper;

import by.ita.chernook.dto.CartItemWebDto;
import by.ita.chernook.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartItemMapper {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    public CartItemWebDto toWebDTO(CartItem cartItem) {
        return CartItemWebDto.builder()
                .uuid(cartItem.getUuid())
                .cart(cartMapper.toWebDTO(cartItem.getCart()))
                .product(productMapper.toWebDTO(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemWebDto cartItemWebDto) {
        return CartItem.builder()
                .uuid(cartItemWebDto.getUuid())
                .cart(cartMapper.toEntity(cartItemWebDto.getCart()))
                .product(productMapper.toEntity(cartItemWebDto.getProduct()))
                .quantity(cartItemWebDto.getQuantity())
                .build();
    }
}
