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
        Customer insertedCustomer = customerService.insertCustomer(customerMapper.toEntity(customerDto));
        return customerMapper.toDTO(insertedCustomer);
    }

    @PutMapping("/update")
    public CustomerDto update(@RequestBody CustomerDto customerDto) {
        Customer updatedCustomer = customerService.updateCustomer(customerMapper.toEntity(customerDto));
        return customerMapper.toDTO(updatedCustomer);
    }

    @GetMapping("/read")
    public CustomerDto read(@RequestParam UUID uuid) {
        Customer customer = customerService.findCustomerById(uuid);
        return customerMapper.toDTO(customer);
    }

    @GetMapping("/read/user/{userUuid}")
    public CustomerDto readByUserUuid(@PathVariable UUID userUuid) {
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

    @DeleteMapping("/delete")
    public CustomerDto delete(@RequestParam UUID uuid) {
        Customer deletedCustomer = customerService.deleteCustomer(uuid);
        return customerMapper.toDTO(deletedCustomer);
    }
}
