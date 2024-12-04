package by.ita.chernook.service;


import by.ita.chernook.model.Customer;
import by.ita.chernook.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer insertCustomer(Customer customer) {
        customer.setCreationDateTime(ZonedDateTime.now());
        return customerRepository.save(customer);
    }
}
