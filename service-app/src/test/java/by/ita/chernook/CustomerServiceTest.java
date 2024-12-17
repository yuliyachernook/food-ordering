package by.ita.chernook;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.dto.to_data_base.UserDatabaseDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.User;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.service.UserService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest extends TestUtils {

    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private UserService userService;

    @Test
    void createCustomer_then_return_insertedCustomer() {
        UUID customerUuid = UUID.randomUUID();
        String login = "login";
        User testUser = User.builder().uuid(UUID.randomUUID()).login(login).password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).build();
        UserDatabaseDto testUserDatabaseDto = UserDatabaseDto.builder().uuid(UUID.randomUUID()).login(login).password("password").build();
        CustomerDatabaseDto testCustomerDatabaseDto = CustomerDatabaseDto.builder().uuid(customerUuid).user(testUserDatabaseDto).cart(CartDatabaseDto.builder().uuid(UUID.randomUUID()).build()).build();

        when(userService.findUserByLogin(login)).thenReturn(null);
        when(customerMapper.toDatabaseDTO(testCustomer)).thenReturn(testCustomerDatabaseDto);
        when(customerMapper.toEntity(restTemplate.postForObject(REQUEST_CUSTOMER_CREATE, testCustomerDatabaseDto, CustomerDatabaseDto.class))).thenReturn(testCustomer);

        Customer actualCustomer = customerService.createCustomer(testCustomer);

        assertEquals(testCustomer, actualCustomer);

        verify(userService, times(1)).findUserByLogin(login);
        verify(customerMapper, times(1)).toDatabaseDTO(testCustomer);
        verify(customerMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_CUSTOMER_CREATE, testCustomerDatabaseDto, CustomerDatabaseDto.class));

    }

    @Test
    void createCustomer_then_throw_IllegalStateException() {
        UUID customerUuid = UUID.randomUUID();
        String login = "login";
        User testUser = User.builder().uuid(UUID.randomUUID()).login(login).password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).build();

        when(userService.findUserByLogin(login)).thenReturn(testUser);

        assertThrows(IllegalStateException.class, () -> customerService.createCustomer(testCustomer));

        verify(userService, times(1)).findUserByLogin(login);
        verify(customerMapper, times(0)).toDatabaseDTO(testCustomer);
    }

    @Test
    void findCustomerById_then_return_existingProduct() {
        UUID customerUuid = UUID.randomUUID();
        String login = "login";
        User testUser = User.builder().uuid(UUID.randomUUID()).login(login).password("password").build();
        Customer testCustomer = Customer.builder().uuid(customerUuid).user(testUser).cart(Cart.builder().uuid(UUID.randomUUID()).build()).build();

        when(customerMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_CUSTOMER_READ, customerUuid), CustomerDatabaseDto.class))).thenReturn(testCustomer);

        Customer actualCustomer = customerService.findCustomerById(customerUuid);

        assertEquals(testCustomer, actualCustomer);

        verify(customerMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_CUSTOMER_READ, customerUuid), CustomerDatabaseDto.class));
    }
}
