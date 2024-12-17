package by.ita.chernook;

import by.ita.chernook.dto.CartItemWebDto;
import by.ita.chernook.dto.CartWebDto;
import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest extends TestUtils {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private CartMapper cartMapper;
    @Mock
    private CartItemMapper cartItemMapper;

    @InjectMocks
    private CartService cartService;

    @Test
    public void createCartTest() {
        Cart testCart = Cart.builder().uuid(UUID.randomUUID()).build();
        CartWebDto testCartWebDto = CartWebDto.builder().uuid(UUID.randomUUID()).build();

        when(cartMapper.toWebDTO(testCart)).thenReturn(testCartWebDto);
        when(restTemplate.postForObject(REQUEST_CART_CREATE, testCartWebDto, CartWebDto.class)).thenReturn(testCartWebDto);
        when(cartMapper.toEntity(testCartWebDto)).thenReturn(testCart);

        Cart actualCart = cartService.createCart(testCart);

        assertEquals(testCart, actualCart);

        verify(restTemplate, times(1)).postForObject(REQUEST_CART_CREATE, testCartWebDto, CartWebDto.class);
        verify(cartMapper).toEntity(testCartWebDto);
    }

    @Test
    public void addCartItemTest() {
        UUID customerUuid = UUID.randomUUID();
        String productId = UUID.randomUUID().toString();
        String quantity = "1";

        cartService.addCartItem(customerUuid, productId, quantity);

        verify(restTemplate).postForEntity(REQUEST_CREATE_CARTITEM + "cartUuid=" + customerUuid + "&productUuid=" + productId + "&quantity=" + quantity, null, Void.class);
    }

    @Test
    public void testUpdateCartItemQuantity() {
        String productUuid = UUID.randomUUID().toString();
        Cart testCart = Cart.builder().uuid(UUID.randomUUID()).build();
        Product testProduct = Product.builder().uuid(UUID.randomUUID()).itemName("Test").build();
        CartItem testCartItem = CartItem.builder().uuid(UUID.randomUUID()).cart(testCart).product(testProduct).quantity(1).build();
        CartWebDto testCartWebDto = CartWebDto.builder().uuid(UUID.randomUUID()).build();
        ProductWebDto testProductWebDto = ProductWebDto.builder().uuid(UUID.randomUUID()).itemName("Test").build();
        CartItemWebDto testCartItemWebDto = CartItemWebDto.builder().uuid(UUID.randomUUID()).cart(testCartWebDto).product(testProductWebDto).quantity(1).build();

        when(restTemplate.postForObject(REQUEST_UPDATE_CARTITEM + "cartUuid=" + testCart.getUuid() + "&productUuid=" + productUuid + "&quantity=" + testCartItem.getQuantity(), null, CartItemWebDto.class)).thenReturn(testCartItemWebDto);
        when(cartItemMapper.toEntity(testCartItemWebDto)).thenReturn(testCartItem);

        CartItem actualCartItem = cartService.updateCartItemQuantity(testCart.getUuid(), productUuid, String.valueOf(testCartItem.getQuantity()));

        assertEquals(testCartItem, actualCartItem);

        verify(restTemplate, times(1)).postForObject(REQUEST_UPDATE_CARTITEM + "cartUuid=" + testCart.getUuid()  + "&productUuid=" + productUuid + "&quantity=" + testCartItem.getQuantity(), null, CartItemWebDto.class);
        verify(cartItemMapper, times(1)).toEntity(testCartItemWebDto);
    }

    @Test
    public void testDeleteCartItemById() {
        String uuid = UUID.randomUUID().toString();

        cartService.deleteCartItemById(uuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_DELETE_CARTITEM, uuid));
    }

    @Test
    public void testCleanCart() {
        UUID cartUuid = UUID.randomUUID();

        cartService.cleanCart(cartUuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_DELETE_ALL_CART_ITEMS_BY_CART_UUID, cartUuid));
    }

    @Test
    public void testTransfer() {
        UUID userCartUuid = UUID.randomUUID();
        UUID tempCartUuid = UUID.randomUUID();

        cartService.transfer(userCartUuid, tempCartUuid);

        verify(restTemplate, times(1)).put(REQUEST_TRANSFER + "userCartUuid=" + userCartUuid + "&tempCartUuid=" + tempCartUuid, null);
    }
}