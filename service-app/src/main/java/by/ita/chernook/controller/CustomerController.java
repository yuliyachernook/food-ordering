package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.CustomerWebDto;
import by.ita.chernook.dto.to_web.DeliveryAddressWebDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/business/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;


    @PostMapping("/create")
    public CustomerWebDto create(@RequestBody CustomerWebDto customerWebDto) {
        Customer customer = customerMapper.toEntity(customerWebDto);
        Customer insertedCustomer = customerService.createCustomer(customer);
        return customerMapper.toWebDTO(insertedCustomer);
    }

    @GetMapping("/read")
    public CustomerWebDto read(@RequestParam UUID uuid) {
        Customer customer = customerService.findCustomerById(uuid);
        return customerMapper.toWebDTO(customer);
    }

    @PostMapping("/add/address")
    public CustomerWebDto addAddress(@RequestParam UUID uuid, @RequestBody DeliveryAddressWebDto deliveryAddressWebDto) {
        Customer insertedCustomer = customerService.addAddress(uuid, deliveryAddressMapper.toEntity(deliveryAddressWebDto));
        return customerMapper.toWebDTO(insertedCustomer);
    }

    @GetMapping("/read/user/{userUuid}")
    public CustomerWebDto readByUserUuid(@PathVariable UUID userUuid) {
        Customer customer = customerService.findCustomerByUserId(userUuid);
        return customerMapper.toWebDTO(customer);
    }

    @GetMapping("/read/all")
    public List<CustomerWebDto> readAll() {
        return customerService.findAll()
                .stream()
                .map(customerMapper::toWebDTO)
                .collect(Collectors.toList());
    }

}
