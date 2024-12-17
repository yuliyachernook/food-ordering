package by.ita.chernook;

import by.ita.chernook.dto.enums.OrderStatusEnum;
import by.ita.chernook.dto.to_data_base.*;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.*;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.service.OrderService;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest extends TestUtils {

    @Mock
    private OrderMapper orderMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;
    @Mock
    private CustomerService customerService;

    @Test
    void createOrder_then_return_insertedProduct() {
        UUID orderUuid = UUID.randomUUID();
        UUID customerUuid = UUID.randomUUID();
        User testUser = User.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(100.0)).build();
        Order testOrder = Order.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItem.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomer).build();
        UserDatabaseDto testUserDatabaseDto = UserDatabaseDto.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        CustomerDatabaseDto testCustomerDatabaseDto = CustomerDatabaseDto.builder().uuid(customerUuid).user(testUserDatabaseDto).cart(CartDatabaseDto.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(100.0)).build();
        OrderDatabaseDto testOrderDatabaseDto = OrderDatabaseDto.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItemDatabaseDto.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomerDatabaseDto).build();

        when(customerService.findCustomerById(customerUuid)).thenReturn(testCustomer);
        when(orderMapper.toDatabaseDTO(testOrder)).thenReturn(testOrderDatabaseDto);
        when(orderMapper.toEntity(restTemplate.postForObject(REQUEST_ORDER_CREATE, testOrderDatabaseDto, OrderDatabaseDto.class))).thenReturn(testOrder);

        Order actualOrder = orderService.createOrder(testOrder, customerUuid);

        assertEquals(testOrder, actualOrder);

        verify(customerService, times(1)).findCustomerById(customerUuid);
        verify(orderMapper, times(1)).toDatabaseDTO(testOrder);
        verify(orderMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_ORDER_CREATE, testOrderDatabaseDto, OrderDatabaseDto.class));
    }

    @Test
    void createOrder_then_throw_IllegalStateException() {
        UUID orderUuid = UUID.randomUUID();
        UUID customerUuid = UUID.randomUUID();
        User testUser = User.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(1.0)).build();
        Order testOrder = Order.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItem.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomer).build();
        UserDatabaseDto testUserDatabaseDto = UserDatabaseDto.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        CustomerDatabaseDto testCustomerDatabaseDto = CustomerDatabaseDto.builder().uuid(customerUuid).user(testUserDatabaseDto).cart(CartDatabaseDto.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(100.0)).build();
        OrderDatabaseDto testOrderDatabaseDto = OrderDatabaseDto.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItemDatabaseDto.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomerDatabaseDto).build();

        when(customerService.findCustomerById(customerUuid)).thenReturn(testCustomer);

        assertThrows(IllegalStateException.class, () ->  orderService.createOrder(testOrder, customerUuid));

        verify(customerService, times(1)).findCustomerById(customerUuid);
        verify(orderMapper, times(0)).toDatabaseDTO(testOrder);
        verify(orderMapper, times(0)).toEntity(restTemplate.postForObject(REQUEST_ORDER_CREATE, testOrderDatabaseDto, OrderDatabaseDto.class));
    }

    @Test
    void updateOrder_then_return_updatedProduct() {
        UUID orderUuid = UUID.randomUUID();
        UUID customerUuid = UUID.randomUUID();
        User testUser = User.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(1.0)).build();
        Order testOrder = Order.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItem.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomer).build();
        UserDatabaseDto testUserDatabaseDto = UserDatabaseDto.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        CustomerDatabaseDto testCustomerDatabaseDto = CustomerDatabaseDto.builder().uuid(customerUuid).user(testUserDatabaseDto).cart(CartDatabaseDto.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(100.0)).build();
        OrderDatabaseDto testOrderDatabaseDto = OrderDatabaseDto.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItemDatabaseDto.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomerDatabaseDto).build();

        when(orderMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_ORDER_READ, orderUuid), OrderDatabaseDto.class))).thenReturn(testOrder);
        when(orderMapper.toDatabaseDTO(testOrder)).thenReturn(testOrderDatabaseDto);

        Order actualOrder = orderService.updateOrder(testOrder);

        assertEquals(testOrder, actualOrder);

        verify(orderMapper, times(2)).toEntity(restTemplate.getForObject(String.format(REQUEST_ORDER_READ, orderUuid), OrderDatabaseDto.class));
        verify(orderMapper, times(1)).toDatabaseDTO(testOrder);
    }

    @Test
    void findOrderById_then_return_existingOrder() {
        UUID orderUuid = UUID.randomUUID();
        UUID customerUuid = UUID.randomUUID();
        User testUser = User.builder().uuid(UUID.randomUUID()).login("login").password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).balance(BigDecimal.valueOf(1.0)).build();
        Order testOrder = Order.builder().uuid(orderUuid).orderStatus(OrderStatusEnum.NEW).totalPrice(BigDecimal.valueOf(55.5)).orderItems(List.of(OrderItem.builder().uuid(UUID.randomUUID()).build())).comment("comment")
                .customer(testCustomer).build();

        when(orderMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_ORDER_READ, orderUuid), OrderDatabaseDto.class))).thenReturn(testOrder);

        Order actualOrder = orderService.findOrderById(orderUuid);

        assertEquals(testOrder, actualOrder);

        verify(orderMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_ORDER_READ, orderUuid), OrderDatabaseDto.class));
    }
}
