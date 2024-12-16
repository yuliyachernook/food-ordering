package by.ita.chernook;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import by.ita.chernook.dto.enums.OrderStatusEnum;
import by.ita.chernook.model.*;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.service.OrderService;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderServiceIT extends TestUtils {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(orderService);
    }

    @Test
    public void createOrder_then_returnCorrect() {
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login("testlogin").password("testpassword").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);
        customerService.rechargeBalance(createdCustomer.getUuid());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        Order expectedOrder = buildOrder(UUID.randomUUID(), List.of(buildOrderItem(UUID.randomUUID(), product, BigDecimal.valueOf(5.5), 2)), null, createdCustomer, OrderStatusEnum.NEW,
        "comment", BigDecimal.valueOf(5.50), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        Order actualOrder = orderService.createOrder(expectedOrder, createdCustomer.getUuid());

        assertNotNull(actualOrder);
        assertEquals(expectedOrder.getOrderStatus(), actualOrder.getOrderStatus());
        assertEquals(expectedOrder.getComment(), actualOrder.getComment());
        assertEquals(expectedOrder.getCustomer().getFirstName(), actualOrder.getCustomer().getFirstName());
        assertFalse(expectedOrder.getOrderItems().isEmpty());
    }

    @Test
    public void updateOrder_then_returnCorrect() {
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login("testlogin2").password("testpassword2").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);
        customerService.rechargeBalance(createdCustomer.getUuid());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        Order order = buildOrder(UUID.randomUUID(), List.of(buildOrderItem(UUID.randomUUID(), product, BigDecimal.valueOf(5.5), 2)), null, createdCustomer, OrderStatusEnum.NEW,
                "comment", BigDecimal.valueOf(5.50), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Order createdOrder = orderService.createOrder(order, createdCustomer.getUuid());
        Order expectedOrder = buildOrder(createdOrder.getUuid(), List.of(buildOrderItem(UUID.randomUUID(), product, BigDecimal.valueOf(5.5), 2)), null, createdCustomer, OrderStatusEnum.PROCESSING,
                "Updated", BigDecimal.valueOf(5.50), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        Order actualOrder = orderService.updateOrder(expectedOrder);

        assertNotNull(actualOrder);
        assertEquals(expectedOrder.getOrderStatus(), actualOrder.getOrderStatus());
        assertEquals(expectedOrder.getComment(), actualOrder.getComment());
        assertEquals(expectedOrder.getCustomer().getFirstName(), actualOrder.getCustomer().getFirstName());
        assertFalse(expectedOrder.getOrderItems().isEmpty());
    }

    @Test
    public void findOrderById_then_returnCorrect() {
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login("testlogin3").password("testpassword3").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);
        customerService.rechargeBalance(createdCustomer.getUuid());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        Order order = buildOrder(UUID.randomUUID(), List.of(buildOrderItem(UUID.randomUUID(), product, BigDecimal.valueOf(5.5), 2)), null, createdCustomer, OrderStatusEnum.NEW,
                "comment", BigDecimal.valueOf(5.50), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Order expectedOrder = orderService.createOrder(order, createdCustomer.getUuid());

        Order actualOrder = orderService.findOrderById(expectedOrder.getUuid());

        assertNotNull(actualOrder);
        assertEquals(expectedOrder.getOrderStatus(), actualOrder.getOrderStatus());
        assertEquals(expectedOrder.getComment(), actualOrder.getComment());
        assertEquals(expectedOrder.getCustomer().getFirstName(), actualOrder.getCustomer().getFirstName());
    }

    @Test
    public void findAllByCustomerUuid_then_returnCorrect() {
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login("testlogin4").password("testpassword4").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);
        customerService.rechargeBalance(createdCustomer.getUuid());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        Order order = buildOrder(UUID.randomUUID(), List.of(buildOrderItem(UUID.randomUUID(), product, BigDecimal.valueOf(5.5), 2)), null, createdCustomer, OrderStatusEnum.NEW,
                "comment", BigDecimal.valueOf(5.50), ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        orderService.createOrder(order, createdCustomer.getUuid());

        List<Order> actualList = orderService.findAllByCustomerUuid(createdCustomer.getUuid());

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
    }
}
