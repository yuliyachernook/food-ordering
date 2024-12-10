package by.ita.chernook;

import by.ita.chernook.model.*;
import by.ita.chernook.repository.CustomerRepository;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest extends TestUtils {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void insertCustomer_then_return() {
        Customer testCustomer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());

        when(customerRepository.save(testCustomer)).thenReturn(testCustomer);

        Customer actualCustomer = customerService.insertCustomer(testCustomer);

        assertEquals(testCustomer, actualCustomer);

        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    void updateCustomer_then_return() {
        UUID customerUuid = UUID.randomUUID();
        Customer testCustomer = buildCustomer(customerUuid, "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());

        when(customerRepository.existsById(customerUuid)).thenReturn(true);
        when(customerRepository.save(testCustomer)).thenReturn(testCustomer);

        Customer actualCustomer = customerService.updateCustomer(testCustomer);

        assertEquals(testCustomer, actualCustomer);

        verify(customerRepository, times(1)).existsById(customerUuid);
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    void updateCustomer_then_throws_NoSuchElementException() {
        UUID customerUuid = UUID.randomUUID();
        Customer testCustomer = buildCustomer(customerUuid, "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());

        when(customerRepository.existsById(customerUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(testCustomer));

        verify(customerRepository, times(1)).existsById(customerUuid);
        verify(customerRepository, times(0)).save(testCustomer);
    }

    @Test
    void deleteCustomer_then_return() {
        UUID customerUuid = UUID.randomUUID();
        Customer testCustomer = buildCustomer(customerUuid, "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());

        when(customerRepository.findById(customerUuid)).thenReturn(Optional.of(testCustomer));
        doNothing().when(customerRepository).deleteById(customerUuid);

        Customer actualCustomer = customerService.deleteCustomer(customerUuid);

        assertEquals(testCustomer, actualCustomer);

        verify(customerRepository, times(1)).findById(customerUuid);
        verify(customerRepository, times(1)).deleteById(customerUuid);
    }

    @Test
    void deleteCustomer_then_throws_NoSuchElementException() {
        UUID customerUuid = UUID.randomUUID();

        when(customerRepository.findById(customerUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> customerService.deleteCustomer(customerUuid));

        verify(customerRepository, times(1)).findById(customerUuid);
        verify(customerRepository, times(0)).deleteById(customerUuid);
    }

    @Test
    void findCustomerById_then_return() {
        UUID customerUuid = UUID.randomUUID();
        Customer testCustomer = buildCustomer(customerUuid, "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());

        when(customerRepository.findById(customerUuid)).thenReturn(Optional.of(testCustomer));

        Customer actualCustomer = customerService.findCustomerById(customerUuid);

        assertEquals(testCustomer, actualCustomer);

        verify(customerRepository, times(1)).findById(customerUuid);
    }

    @Test
    void findCustomerById_then_throws_NoSuchElementException() {
        UUID customerUuid = UUID.randomUUID();

        when(customerRepository.findById(customerUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> customerService.findCustomerById(customerUuid));

        verify(customerRepository, times(1)).findById(customerUuid);
    }

    @Test
    void findCustomerByUserUuid_then_return() {
        UUID userUuid = UUID.randomUUID();
        Customer testCustomer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());

        when(customerRepository.findByUserUuid(userUuid)).thenReturn(Optional.of(testCustomer));

        Customer actualCustomer = customerService.findCustomerByUserUuid(userUuid);

        assertEquals(testCustomer, actualCustomer);

        verify(customerRepository, times(1)).findByUserUuid(userUuid);
    }

    @Test
    void findCustomerByUserUuid_then_throws_NoSuchElementException() {
        UUID userUuid = UUID.randomUUID();

        when(customerRepository.findByUserUuid(userUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> customerService.findCustomerByUserUuid(userUuid));

        verify(customerRepository, times(1)).findByUserUuid(userUuid);
    }

    @Test
    void findAll_then_return() {
        Customer testCustomer = buildCustomer(UUID.randomUUID(), "FirstName", "LastName", "375291234567", "test@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());
        Customer testCustomer2 = buildCustomer(UUID.randomUUID(), "FirstName2", "LastName2", "375297654321", "test2@mail.ru",
                new User(), List.of(new DeliveryAddress()), new Cart());
        List<Customer> testList = new ArrayList<>(Arrays.asList(testCustomer, testCustomer2));

        when(customerRepository.findAll()).thenReturn(testList);

        List<Customer> actualList = customerService.findAll();

        assertIterableEquals(testList, actualList);

        verify(customerRepository, times(1)).findAll();
    }
}
