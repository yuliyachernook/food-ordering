package by.ita.chernook;

import by.ita.chernook.dto.enums.OrderStatusEnum;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.DeliveryAddress;
import by.ita.chernook.model.Order;
import by.ita.chernook.model.OrderItem;
import by.ita.chernook.repository.OrderRepository;
import by.ita.chernook.service.OrderService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest extends TestUtils {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void insertOrder_then_return() {
        Order testOrder = buildOrder(UUID.randomUUID(), List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment", BigDecimal.valueOf(25.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        Order actualOrder = orderService.insertOrder(testOrder);

        assertEquals(testOrder, actualOrder);

        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void updateOrder_then_return() {
        UUID orderUuid = UUID.randomUUID();
        Order testOrder = buildOrder(orderUuid, List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment", BigDecimal.valueOf(25.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(orderRepository.existsById(orderUuid)).thenReturn(true);
        when(orderRepository.save(testOrder)).thenReturn(testOrder);

        Order actualOrder = orderService.updateOrder(testOrder);

        assertEquals(testOrder, actualOrder);

        verify(orderRepository, times(1)).existsById(orderUuid);
        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void updateOrder_then_throws_NoSuchElementException() {
        UUID orderUuid = UUID.randomUUID();
        Order testOrder = buildOrder(orderUuid, List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment", BigDecimal.valueOf(55.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(orderRepository.existsById(orderUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> orderService.updateOrder(testOrder));

        verify(orderRepository, times(1)).existsById(orderUuid);
        verify(orderRepository, times(0)).save(testOrder);
    }

    @Test
    void deleteOrder_then_return() {
        UUID orderUuid = UUID.randomUUID();
        Order testOrder = buildOrder(orderUuid, List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment", BigDecimal.valueOf(55.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(orderRepository.findById(orderUuid)).thenReturn(Optional.of(testOrder));
        doNothing().when(orderRepository).deleteById(orderUuid);

        Order actualOrder = orderService.deleteOrder(orderUuid);

        assertEquals(testOrder, actualOrder);

        verify(orderRepository, times(1)).findById(orderUuid);
        verify(orderRepository, times(1)).deleteById(orderUuid);
    }

    @Test
    void deleteOrder_then_throws_NoSuchElementException() {
        UUID orderUuid = UUID.randomUUID();

        when(orderRepository.findById(orderUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> orderService.deleteOrder(orderUuid));

        verify(orderRepository, times(1)).findById(orderUuid);
        verify(orderRepository, times(0)).deleteById(orderUuid);
    }

    @Test
    void findOrderById_then_return() {
        UUID orderUuid = UUID.randomUUID();
        Order testOrder = buildOrder(orderUuid, List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment", BigDecimal.valueOf(25.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(orderRepository.findById(orderUuid)).thenReturn(Optional.of(testOrder));

        Order actualOrder = orderService.findOrderById(orderUuid);

        assertEquals(testOrder, actualOrder);

        verify(orderRepository, times(1)).findById(orderUuid);
    }

    @Test
    void findOrderById_then_throws_NoSuchElementException() {
        UUID orderUuid = UUID.randomUUID();

        when(orderRepository.findById(orderUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> orderService.findOrderById(orderUuid));

        verify(orderRepository, times(1)).findById(orderUuid);
    }

    @Test
    void findAll_then_return() {
        Order testOrder = buildOrder(UUID.randomUUID(), List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.PROCESSING, "Test comment", BigDecimal.valueOf(25.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Order testOrder2 = buildOrder(UUID.randomUUID(), List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment2", BigDecimal.valueOf(48.8), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        List<Order> testList = new ArrayList<>(Arrays.asList(testOrder, testOrder2));

        when(orderRepository.findAll()).thenReturn(testList);

        List<Order> actualList = orderService.findAll();

        assertIterableEquals(testList, actualList);

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findAlLByCustomerUuid_then_return() {
        UUID customerUUid = UUID.randomUUID();
        Order testOrder = buildOrder(UUID.randomUUID(), List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.PROCESSING, "Test comment", BigDecimal.valueOf(25.2), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Order testOrder2 = buildOrder(UUID.randomUUID(), List.of(new OrderItem()), new DeliveryAddress(), new Customer(),
                OrderStatusEnum.NEW, "Test comment2", BigDecimal.valueOf(48.8), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        List<Order> testList = new ArrayList<>(Arrays.asList(testOrder, testOrder2));

        when(orderRepository.findAllByCustomerUuid(customerUUid)).thenReturn(testList);

        List<Order> actualList = orderService.findAllByCustomerUuid(customerUUid);

        assertIterableEquals(testList, actualList);

        verify(orderRepository, times(1)).findAllByCustomerUuid(customerUUid);
    }
}