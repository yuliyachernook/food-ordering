package by.ita.chernook;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_data_base.CartItemDatabaseDto;
import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.CartItemService;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.CustomerService;
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
    private CartItemMapper cartItemMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartItemService cartItemService;
    @Mock
    private CartService cartService;
    @Mock
    private ProductService productService;

    @Test
    void createCart_then_return_insertedCartItem() {
        Cart testCart = Cart.builder().uuid(UUID.randomUUID()).build();

        when(cartService.findCartById(testCart.getUuid())).thenReturn(testCart);
        when(productService.findProductById(testProduct.getUuid())).thenReturn(testProduct);
        when(cartItemMapper.toEntity(restTemplate.postForObject(REQUEST_CART_ITEM_CREATE, cartItemMapper.toDatabaseDTO(testCartItem), CartItemDatabaseDto.class)))
                .thenReturn(testCartItem);

        CartItem actualCartItem = cartItemService.createCartItem(testCart.getUuid(), testProduct.getUuid(), 1);

        assertEquals(testCartItem, actualCartItem);

        verify(cartService, times(1)).findCartById(testCart.getUuid());
        verify(productService, times(1)).findProductById(testProduct.getUuid());
        verify(cartItemMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_CART_ITEM_CREATE,
                cartItemMapper.toDatabaseDTO(testCartItem), CartItemDatabaseDto.class));
    }

    @Test
    void updateCartItem_then_return_updatedCartItem() {
        Cart testCart = Cart.builder().uuid(UUID.randomUUID()).build();
        Product testProduct = Product.builder().uuid(UUID.randomUUID()).itemName("Test").build();
        CartItem testCartItem = CartItem.builder().uuid(UUID.randomUUID()).cart(testCart).product(testProduct).quantity(1).build();
        CartDatabaseDto testCartDatabaseDto = CartDatabaseDto.builder().uuid(UUID.randomUUID()).build();
        ProductDatabaseDto testProductDatabaseDto = ProductDatabaseDto.builder().uuid(UUID.randomUUID()).itemName("Test").build();
        CartItemDatabaseDto testCartItemDatabaseDto = CartItemDatabaseDto.builder().uuid(UUID.randomUUID()).cart(testCartDatabaseDto).product(testProductDatabaseDto).quantity(1).build();
        String url = String.format(REQUEST_CART_ITEM_READ_BY_CART_UUID_AND_PRODUCT_UUID, testCart.getUuid(), testProduct.getUuid());

        when(restTemplate.getForObject(eq(url), eq(CartItemDatabaseDto.class))).thenReturn(testCartItemDatabaseDto);
        when(cartItemMapper.toEntity(testCartItemDatabaseDto)).thenReturn(testCartItem);
        when(restTemplate.getForObject(eq(String.format(REQUEST_CART_ITEM_READ, testCartItem.getUuid())), eq(CartItemDatabaseDto.class)))
                .thenReturn(testCartItemDatabaseDto);

        CartItem actualCartItem = cartItemService.updateCartItem(testCart.getUuid(), testProduct.getUuid(), 2);

        assertEquals(testCartItem.getUuid(), actualCartItem.getUuid());
        assertEquals(2, actualCartItem.getQuantity());

        verify(restTemplate, times(1)).getForObject(url, CartItemDatabaseDto.class);
        verify(cartItemMapper, times(2)).toEntity(testCartItemDatabaseDto);
        verify(restTemplate, times(1)).put(REQUEST_CART_ITEM_UPDATE, cartItemMapper.toDatabaseDTO(testCartItem), CartItemDatabaseDto.class);
        verify(restTemplate, times(1)).getForObject(String.format(REQUEST_CART_ITEM_READ, testCartItem.getUuid()), CartItemDatabaseDto.class);
    }

    @Test
    void deleteCartItem() {
        UUID uuid = UUID.randomUUID();
        cartItemService.deleteCartItem(uuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_CART_ITEM_DELETE, uuid));
    }
}
