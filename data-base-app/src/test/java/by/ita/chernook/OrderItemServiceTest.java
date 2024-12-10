package by.ita.chernook;

import by.ita.chernook.model.OrderItem;
import by.ita.chernook.model.Product;
import by.ita.chernook.repository.OrderItemRepository;
import by.ita.chernook.service.OrderItemService;
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
public class OrderItemServiceTest extends TestUtils {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void insertOrderItem_then_return() {
        OrderItem testOrderItem = buildOrderItem(UUID.randomUUID(), new Product(), 25.5, 2);

        when(orderItemRepository.save(testOrderItem)).thenReturn(testOrderItem);

        OrderItem actualOrderItem = orderItemService.insertOrderItem(testOrderItem);

        assertEquals(testOrderItem, actualOrderItem);

        verify(orderItemRepository, times(1)).save(testOrderItem);
    }

    @Test
    void updateOrderItem_then_return() {
        UUID orderItemUuid = UUID.randomUUID();
        OrderItem testOrderItem = buildOrderItem(orderItemUuid, new Product(), 25.5, 2);

        when(orderItemRepository.existsById(orderItemUuid)).thenReturn(true);
        when(orderItemRepository.save(testOrderItem)).thenReturn(testOrderItem);

        OrderItem actualOrderItem = orderItemService.updateOrderItem(testOrderItem);

        assertEquals(testOrderItem, actualOrderItem);

        verify(orderItemRepository, times(1)).existsById(orderItemUuid);
        verify(orderItemRepository, times(1)).save(testOrderItem);
    }

    @Test
    void updateOrderItem_then_throws_NoSuchElementException() {
        UUID orderItemUuid = UUID.randomUUID();
        OrderItem testOrderItem = buildOrderItem(orderItemUuid, new Product(), 25.5, 2);

        when(orderItemRepository.existsById(orderItemUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> orderItemService.updateOrderItem(testOrderItem));

        verify(orderItemRepository, times(1)).existsById(orderItemUuid);
        verify(orderItemRepository, times(0)).save(testOrderItem);
    }

    @Test
    void deleteOrderItem_then_return() {
        UUID orderItemUuid = UUID.randomUUID();
        OrderItem testOrderItem = buildOrderItem(orderItemUuid, new Product(), 25.5, 2);

        when(orderItemRepository.findById(orderItemUuid)).thenReturn(Optional.of(testOrderItem));
        doNothing().when(orderItemRepository).deleteById(orderItemUuid);

        OrderItem actualOrderItem = orderItemService.deleteOrderItem(orderItemUuid);

        assertEquals(testOrderItem, actualOrderItem);

        verify(orderItemRepository, times(1)).findById(orderItemUuid);
        verify(orderItemRepository, times(1)).deleteById(orderItemUuid);
    }

    @Test
    void deleteOrderItem_then_throws_NoSuchElementException() {
        UUID orderItemUuid = UUID.randomUUID();

        when(orderItemRepository.findById(orderItemUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> orderItemService.deleteOrderItem(orderItemUuid));

        verify(orderItemRepository, times(1)).findById(orderItemUuid);
        verify(orderItemRepository, times(0)).deleteById(orderItemUuid);
    }

    @Test
    void findOrderItemById_then_return() {
        UUID orderItemUuid = UUID.randomUUID();
        OrderItem testOrderItem = buildOrderItem(orderItemUuid, new Product(), 25.5, 2);

        when(orderItemRepository.findById(orderItemUuid)).thenReturn(Optional.of(testOrderItem));

        OrderItem actualOrderItem = orderItemService.findOrderItemById(orderItemUuid);

        assertEquals(testOrderItem, actualOrderItem);

        verify(orderItemRepository, times(1)).findById(orderItemUuid);
    }

    @Test
    void findOrderItemById_then_throws_NoSuchElementException() {
        UUID orderItemUuid = UUID.randomUUID();

        when(orderItemRepository.findById(orderItemUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> orderItemService.findOrderItemById(orderItemUuid));

        verify(orderItemRepository, times(1)).findById(orderItemUuid);
    }

    @Test
    void findAlLByOrderUuid_then_return() {
        UUID orderUUid = UUID.randomUUID();
        OrderItem testOrderItem = buildOrderItem(UUID.randomUUID(), new Product(), 25.5, 2);
        OrderItem testOrderItem2 = buildOrderItem(UUID.randomUUID(), new Product(), 44.3, 4);

        List<OrderItem> testList = new ArrayList<>(Arrays.asList(testOrderItem, testOrderItem2));

        when(orderItemRepository.findAllByOrderUuid(orderUUid)).thenReturn(testList);

        List<OrderItem> actualList = orderItemService.findAllByOrderUuid(orderUUid);

        assertIterableEquals(testList, actualList);

        verify(orderItemRepository, times(1)).findAllByOrderUuid(orderUUid);
    }
}
