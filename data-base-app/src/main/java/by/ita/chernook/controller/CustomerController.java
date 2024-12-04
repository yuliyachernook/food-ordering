package by.ita.chernook.controller;

import by.ita.chernook.dto.CustomerDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/database/customers")
public class CustomerController {

    private final CustomerService productService;
    private final CustomerMapper productMapper;

    @PostMapping("/create")
    public CustomerDto create(@RequestBody CustomerDto customerDto) {
        Customer customer = productMapper.toEntity(customerDto);
        Customer insertedCustomer = productService.insertCustomer(customer);
        return productMapper.toDTO(insertedCustomer);
    }
}
