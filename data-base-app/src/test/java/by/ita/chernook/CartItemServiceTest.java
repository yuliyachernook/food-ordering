package by.ita.chernook;

import by.ita.chernook.model.Cart;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.Product;
import by.ita.chernook.repository.CartItemRepository;
import by.ita.chernook.service.CartItemService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest extends TestUtils {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemService cartItemService;

    @Test
    void insertCartItem_then_return() {
        CartItem testCartItem = buildCartItem(UUID.randomUUID(), new Cart(), new Product(), 2);

        when(cartItemRepository.save(testCartItem)).thenReturn(testCartItem);

        CartItem actualCartItem = cartItemService.insertCartItem(testCartItem);

        assertEquals(testCartItem, actualCartItem);

        verify(cartItemRepository, times(1)).save(testCartItem);
    }

    @Test
    void updateCartItem_then_return() {
        UUID cartItemUuid = UUID.randomUUID();
        CartItem testCartItem = buildCartItem(cartItemUuid, new Cart(), new Product(), 2);

        when(cartItemRepository.existsById(cartItemUuid)).thenReturn(true);
        when(cartItemRepository.save(testCartItem)).thenReturn(testCartItem);

        CartItem actualCartItem = cartItemService.updateCartItem(testCartItem);

        assertEquals(testCartItem, actualCartItem);

        verify(cartItemRepository, times(1)).existsById(cartItemUuid);
        verify(cartItemRepository, times(1)).save(testCartItem);
    }

    @Test
    void updateCartItem_then_throws_NoSuchElementException() {
        UUID cartItemUuid = UUID.randomUUID();
        CartItem testCartItem = buildCartItem(cartItemUuid, new Cart(), new Product(), 2);

        when(cartItemRepository.existsById(cartItemUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> cartItemService.updateCartItem(testCartItem));

        verify(cartItemRepository, times(1)).existsById(cartItemUuid);
        verify(cartItemRepository, times(0)).save(testCartItem);
    }

    @Test
    void deleteCartItem_then_return() {
        UUID cartItemUuid = UUID.randomUUID();
        CartItem testCartItem = buildCartItem(cartItemUuid, new Cart(), new Product(), 2);

        when(cartItemRepository.findById(cartItemUuid)).thenReturn(Optional.of(testCartItem));
        doNothing().when(cartItemRepository).deleteById(cartItemUuid);

        CartItem actualCartItem = cartItemService.deleteCartItem(cartItemUuid);

        assertEquals(testCartItem, actualCartItem);

        verify(cartItemRepository, times(1)).findById(cartItemUuid);
        verify(cartItemRepository, times(1)).deleteById(cartItemUuid);
    }

    @Test
    void deleteCartItem_then_throws_NoSuchElementException() {
        UUID cartItemUuid = UUID.randomUUID();

        when(cartItemRepository.findById(cartItemUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> cartItemService.deleteCartItem(cartItemUuid));

        verify(cartItemRepository, times(1)).findById(cartItemUuid);
        verify(cartItemRepository, times(0)).deleteById(cartItemUuid);
    }

    @Test
    void findCartItemById_then_return() {
        UUID cartItemUuid = UUID.randomUUID();
        CartItem testCartItem = buildCartItem(cartItemUuid, new Cart(), new Product(), 2);

        when(cartItemRepository.findById(cartItemUuid)).thenReturn(Optional.of(testCartItem));

        CartItem actualCartItem = cartItemService.findCartItemById(cartItemUuid);

        assertEquals(testCartItem, actualCartItem);

        verify(cartItemRepository, times(1)).findById(cartItemUuid);
    }

    @Test
    void findCartItemById_then_throws_NoSuchElementException() {
        UUID cartItemUuid = UUID.randomUUID();

        when(cartItemRepository.findById(cartItemUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> cartItemService.findCartItemById(cartItemUuid));

        verify(cartItemRepository, times(1)).findById(cartItemUuid);
    }

    @Test
    void findCartItemByCartUuidAndProductUuid_then_return() {
        UUID cartUuid = UUID.randomUUID();
        UUID productUuid = UUID.randomUUID();
        CartItem testCartItem = buildCartItem(productUuid, Cart.builder().uuid(cartUuid).build(), Product.builder().uuid(productUuid).build(), 2);

        when(cartItemRepository.findByCartUuidAndProductUuid(cartUuid, productUuid)).thenReturn(Optional.of(testCartItem));

        CartItem actualCartItem = cartItemService.findByCartUuidAndProductUuid(cartUuid, productUuid);

        assertEquals(testCartItem, actualCartItem);

        verify(cartItemRepository, times(1)).findByCartUuidAndProductUuid(cartUuid, productUuid);
    }

    @Test
    void findCartItemByCartUuidAndProductUuid_then_throws_NoSuchElementException() {
        UUID cartItemUuid = UUID.randomUUID();
        UUID productUuid = UUID.randomUUID();

        when(cartItemRepository.findByCartUuidAndProductUuid(cartItemUuid, productUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> cartItemService.findByCartUuidAndProductUuid(cartItemUuid, productUuid));

        verify(cartItemRepository, times(1)).findByCartUuidAndProductUuid(cartItemUuid, productUuid);
    }

    @Test
    void findAllByCartUuid_then_return() {
        UUID cartUuid = UUID.randomUUID();
        CartItem testCartItem = buildCartItem(UUID.randomUUID(), Cart.builder().uuid(cartUuid).build(), Product.builder().uuid(UUID.randomUUID()).build(), 2);
        CartItem testCartItem2 = buildCartItem(UUID.randomUUID(), Cart.builder().uuid(cartUuid).build(), Product.builder().uuid(UUID.randomUUID()).build(), 2);

        List<CartItem> testList = new ArrayList<>(Arrays.asList(testCartItem, testCartItem2));

        when(cartItemRepository.findByCartUuid(cartUuid)).thenReturn(testList);

        List<CartItem> actualList = cartItemService.findAllByCartUuid(cartUuid);

        assertIterableEquals(testList, actualList);

        verify(cartItemRepository, times(1)).findByCartUuid(cartUuid);
    }
}
