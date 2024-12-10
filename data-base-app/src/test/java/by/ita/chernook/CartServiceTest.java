package by.ita.chernook;

import by.ita.chernook.model.Cart;
import by.ita.chernook.repository.CartRepository;
import by.ita.chernook.service.CartService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest extends TestUtils {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void insertCart_then_return() {
        Cart testCart = buildCart(UUID.randomUUID(), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(cartRepository.save(testCart)).thenReturn(testCart);

        Cart actualCart = cartService.insertCart(testCart);

        assertEquals(testCart, actualCart);

        verify(cartRepository, times(1)).save(testCart);
    }

    @Test
    void updateCart_then_return() {
        UUID cartUuid = UUID.randomUUID();
        Cart testCart = buildCart(cartUuid, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(cartRepository.existsById(cartUuid)).thenReturn(true);
        when(cartRepository.save(testCart)).thenReturn(testCart);

        Cart actualCart = cartService.updateCart(testCart);

        assertEquals(testCart, actualCart);

        verify(cartRepository, times(1)).existsById(cartUuid);
        verify(cartRepository, times(1)).save(testCart);
    }

    @Test
    void updateCart_then_throws_NoSuchElementException() {
        UUID cartUuid = UUID.randomUUID();
        Cart testCart = buildCart(cartUuid, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(cartRepository.existsById(cartUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> cartService.updateCart(testCart));

        verify(cartRepository, times(1)).existsById(cartUuid);
        verify(cartRepository, times(0)).save(testCart);
    }

    @Test
    void deleteCart_then_return() {
        UUID cartUuid = UUID.randomUUID();
        Cart testCart = buildCart(cartUuid, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(cartRepository.findById(cartUuid)).thenReturn(Optional.of(testCart));
        doNothing().when(cartRepository).deleteById(cartUuid);

        Cart actualCart = cartService.deleteCart(cartUuid);

        assertEquals(testCart, actualCart);

        verify(cartRepository, times(1)).findById(cartUuid);
        verify(cartRepository, times(1)).deleteById(cartUuid);
    }

    @Test
    void deleteCart_then_throws_NoSuchElementException() {
        UUID cartUuid = UUID.randomUUID();

        when(cartRepository.findById(cartUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> cartService.deleteCart(cartUuid));

        verify(cartRepository, times(1)).findById(cartUuid);
        verify(cartRepository, times(0)).deleteById(cartUuid);
    }

    @Test
    void findCartById_then_return() {
        UUID cartUuid = UUID.randomUUID();
        Cart testCart = buildCart(cartUuid, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(cartRepository.findById(cartUuid)).thenReturn(Optional.of(testCart));

        Cart actualCart = cartService.findCartById(cartUuid);

        assertEquals(testCart, actualCart);

        verify(cartRepository, times(1)).findById(cartUuid);
    }

    @Test
    void findCartById_then_throws_NoSuchElementException() {
        UUID cartUuid = UUID.randomUUID();

        when(cartRepository.findById(cartUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> cartService.findCartById(cartUuid));

        verify(cartRepository, times(1)).findById(cartUuid);
    }

    @Test
    void findAll_then_return() {
        Cart testCart = buildCart(UUID.randomUUID(), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Cart testCart2 = buildCart(UUID.randomUUID(), ZonedDateTime.parse("2024-09-09T00:28:58.345+03:00"));
        List<Cart> testList = new ArrayList<>(Arrays.asList(testCart, testCart2));

        when(cartRepository.findAll()).thenReturn(testList);

        List<Cart> actualList = cartService.findAll();

        assertIterableEquals(testList, actualList);

        verify(cartRepository, times(1)).findAll();
    }
}