package by.ita.chernook.controller;

import by.ita.chernook.dto.CustomerDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping("/create")
    public CustomerDto create(@RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer insertedCustomer = customerService.insertCustomer(customer);
        return customerMapper.toDTO(insertedCustomer);
    }

    @PostMapping("/update")
    public CustomerDto update(@RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        return customerMapper.toDTO(updatedCustomer);
    }

    @GetMapping("/read")
    public CustomerDto read(@RequestParam UUID id) {
        Customer customer = customerService.findCustomerById(id);
        return customerMapper.toDTO(customer);
    }

    @GetMapping("/read/useruuid")
    public CustomerDto readByUserUuid(@RequestParam UUID userUuid) {
        Customer customer = customerService.findCustomerByUserUuid(userUuid);
        return customerMapper.toDTO(customer);
    }

    @GetMapping("/read/all")
    public List<CustomerDto> readAll() {
        return customerService.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
