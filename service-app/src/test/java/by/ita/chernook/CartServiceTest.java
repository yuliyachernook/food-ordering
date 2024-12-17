package by.ita.chernook;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_data_base.CartItemDatabaseDto;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.CartItemService;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest extends TestUtils {

    @Mock
    private CartMapper cartMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartService cartService;

    @Test
    void createCart_then_return_insertedCart() {
        Cart testCart = Cart.builder().uuid(UUID.randomUUID()).build();
        CartDatabaseDto testCartDatabaseDto = CartDatabaseDto.builder().uuid(UUID.randomUUID()).build();

        when(cartMapper.toDatabaseDTO(testCart)).thenReturn(testCartDatabaseDto);
        when(cartMapper.toEntity(restTemplate.postForObject(REQUEST_CART_CREATE, testCartDatabaseDto, CartDatabaseDto.class))).thenReturn(testCart);

        Cart actualCart = cartService.createCart(testCart);

        assertEquals(testCart, actualCart);

        verify(cartMapper, times(1)).toDatabaseDTO(testCart);
        verify(cartMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_CART_CREATE, testCartDatabaseDto, CartDatabaseDto.class));
    }

    @Test
    void updateCart_then_return_updatedCart() {
        Cart testCart = Cart.builder().uuid(UUID.randomUUID()).build();

        when(cartMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_CART_READ, testCart.getUuid()), CartDatabaseDto.class)))
                .thenReturn(testCart);

        Cart actualCart = cartService.findCartById(testCart.getUuid());

        assertEquals(testCart, actualCart);

        verify(cartMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_CART_READ, testCart.getUuid()), CartDatabaseDto.class));
    }

    @Test
    void deleteCart() {
        UUID uuid = UUID.randomUUID();
        cartService.deleteCart(uuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_CART_DELETE, uuid));
    }
}
