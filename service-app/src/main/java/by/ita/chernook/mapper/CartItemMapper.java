package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.CartItemDatabaseDto;
import by.ita.chernook.dto.to_web.CartItemWebDto;
import by.ita.chernook.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartItemMapper {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    public CartItemDatabaseDto toDatabaseDTO(CartItem cartItem) {
        return CartItemDatabaseDto.builder()
                .uuid(cartItem.getUuid())
                .cart(cartMapper.toDatabaseDTO(cartItem.getCart()))
                .product(productMapper.toDatabaseDTO(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItemWebDto toWebDTO(CartItem cartItem) {
        return CartItemWebDto.builder()
                .uuid(cartItem.getUuid())
                .cart(cartMapper.toWebDTO(cartItem.getCart()))
                .product(productMapper.toWebDTO(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemDatabaseDto cartItemDatabaseDto) {
        return CartItem.builder()
                .uuid(cartItemDatabaseDto.getUuid())
                .cart(cartMapper.toEntity(cartItemDatabaseDto.getCart()))
                .product(productMapper.toEntity(cartItemDatabaseDto.getProduct()))
                .quantity(cartItemDatabaseDto.getQuantity())
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
