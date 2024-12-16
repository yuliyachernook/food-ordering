package by.ita.chernook;

import by.ita.chernook.model.*;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerServiceIT extends TestUtils {

    @Autowired
    private CustomerService customerService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(customerService);
    }

    @Test
    public void createCustomer_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer expectedCustomer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password1").build(), null, Cart.builder().build());

        Customer actualCustomer = customerService.createCustomer(expectedCustomer);

        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
        assertEquals(expectedCustomer.getUser().getLogin(), actualCustomer.getUser().getLogin());
        assertEquals(expectedCustomer.getUser().getPassword(), actualCustomer.getUser().getPassword());
    }

    @Test
    public void updateCustomer_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);
        Customer expectedCustomer = buildCustomer(createdCustomer.getUuid(), "Updated FirstName", "LastName", "375297654321", "test@mail.ru",
                User.builder().login(login).password("password").build(), null, Cart.builder().build());

        Customer actualCustomer = customerService.updateCustomer(expectedCustomer);

        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
        assertEquals(expectedCustomer.getDeliveryAddresses(), actualCustomer.getDeliveryAddresses());
        assertEquals(expectedCustomer.getUser().getLogin(), actualCustomer.getUser().getLogin());
        assertEquals(expectedCustomer.getUser().getPassword(), actualCustomer.getUser().getPassword());
    }

    @Test
    public void findCouponById_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password3").build(), null, Cart.builder().build());
        Customer expectedCustomer = customerService.createCustomer(customer);

        Customer actualCustomer = customerService.findCustomerById(expectedCustomer.getUuid());

        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
        assertEquals(expectedCustomer.getDeliveryAddresses(), actualCustomer.getDeliveryAddresses());
        assertEquals(expectedCustomer.getUser().getLogin(), actualCustomer.getUser().getLogin());
        assertEquals(expectedCustomer.getUser().getPassword(), actualCustomer.getUser().getPassword());
    }

    @Test
    public void findCouponByCode_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password4").build(), null, Cart.builder().build());
        Customer expectedCustomer = customerService.createCustomer(customer);

        Customer actualCustomer = customerService.findCustomerByUserId(expectedCustomer.getUser().getUuid());

        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(expectedCustomer.getPhoneNumber(), actualCustomer.getPhoneNumber());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
        assertEquals(expectedCustomer.getDeliveryAddresses(), actualCustomer.getDeliveryAddresses());
        assertEquals(expectedCustomer.getUser().getLogin(), actualCustomer.getUser().getLogin());
        assertEquals(expectedCustomer.getUser().getPassword(), actualCustomer.getUser().getPassword());
    }

    @Test
    public void findAllCoupons_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password5").build(), null, Cart.builder().build());
        Customer expectedCustomer = customerService.createCustomer(customer);
        List<Customer> expectedList = List.of(expectedCustomer);

        List<Customer> actualList = customerService.findAll();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
        assertTrue(expectedList.contains(expectedCustomer));
    }

    @Test
    public void addAddress_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password6").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);
        DeliveryAddress deliveryAddress = buildDeliveryAddress(UUID.randomUUID(), "Test city", "Test street", "20а", 15);

        Customer actualCustomer = customerService.addAddress(createdCustomer.getUuid(), deliveryAddress);

        assertNotNull(actualCustomer);
        assertEquals(createdCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(createdCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(createdCustomer.getUser().getLogin(), actualCustomer.getUser().getLogin());
        assertFalse(actualCustomer.getDeliveryAddresses().isEmpty());
    }

    @Test
    public void rechargeBalance_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        Customer customer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                User.builder().login(login).password("password7").build(), null, Cart.builder().build());
        Customer createdCustomer = customerService.createCustomer(customer);

        Customer actualCustomer = customerService.rechargeBalance(createdCustomer.getUuid());

        assertNotNull(actualCustomer);
        assertEquals(createdCustomer.getFirstName(), actualCustomer.getFirstName());
        assertEquals(createdCustomer.getLastName(), actualCustomer.getLastName());
        assertEquals(createdCustomer.getUser().getLogin(), actualCustomer.getUser().getLogin());
        assertTrue(actualCustomer.getBalance().compareTo(BigDecimal.valueOf(0)) > 0);
    }
}
