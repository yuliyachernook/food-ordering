package by.ita.chernook.service;

import by.ita.chernook.model.Customer;
import by.ita.chernook.model.Order;
import by.ita.chernook.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer insertCustomer(Customer customer) {
        customer.setCreationDateTime(ZonedDateTime.now());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        if (!customerRepository.existsById(customer.getUuid())) {
            throw new NoSuchElementException(String.format("Customer with UUID: %s not found", customer.getUuid()));
        }
        if (customer.getDeliveryAddresses() != null) {
            customer.getDeliveryAddresses().forEach(address -> address.setCustomer(customer));
        }
        return customerRepository.save(customer);
    }

    public Customer findCustomerById(UUID userUuid) {
        return customerRepository.findById(userUuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Customer with id: %s not found", userUuid)));
    }

    public Customer findCustomerByUserUuid(UUID userUuid) {
        return customerRepository.findByUserUuid(userUuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Customer with userUuid: %s not found", userUuid)));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer deleteCustomer(UUID uuid) {
        Customer customer = findCustomerById(uuid);
        customerRepository.deleteById(uuid);
        return customer;
    }
}
