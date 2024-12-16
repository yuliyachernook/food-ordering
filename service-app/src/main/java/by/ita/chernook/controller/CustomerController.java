package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.CustomerWebDto;
import by.ita.chernook.dto.to_web.DeliveryAddressWebDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.service.DeliveryAddressService;
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
    private final DeliveryAddressService deliveryAddressService;
    private final CustomerMapper customerMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    @PostMapping("/create")
    public CustomerWebDto create(@RequestBody CustomerWebDto customerWebDto) {
        Customer insertedCustomer = customerService.createCustomer(customerMapper.toEntity(customerWebDto));
        return customerMapper.toWebDTO(insertedCustomer);
    }

    @PostMapping("/update")
    public CustomerWebDto update(@RequestBody CustomerWebDto customerWebDto) {
        Customer updatedCustomer = customerService.updateCustomer(customerMapper.toEntity(customerWebDto));
        return customerMapper.toWebDTO(updatedCustomer);
    }

    @GetMapping("/read")
    public CustomerWebDto read(@RequestParam UUID uuid) {
        Customer customer = customerService.findCustomerById(uuid);
        return customerMapper.toWebDTO(customer);
    }

    @GetMapping("/read/user/{userUuid}")
    public CustomerWebDto readByUserUuid(@PathVariable UUID userUuid) {
        Customer customer = customerService.findCustomerByUserId(userUuid);
        return customerMapper.toWebDTO(customer);
    }

    @PostMapping("/recharge/balance")
    public CustomerWebDto rechargeBalance(@RequestParam UUID uuid) {
        Customer customer = customerService.rechargeBalance(uuid);
        return customerMapper.toWebDTO(customer);
    }

    @GetMapping("/read/all")
    public List<CustomerWebDto> readAll() {
        return customerService.findAll()
                .stream()
                .map(customerMapper::toWebDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add/address")
    public CustomerWebDto addAddress(@RequestParam UUID uuid, @RequestBody DeliveryAddressWebDto deliveryAddressWebDto) {
        Customer customer = customerService.addAddress(uuid, deliveryAddressMapper.toEntity(deliveryAddressWebDto));
        return customerMapper.toWebDTO(customer);
    }

    @DeleteMapping("/delete")
    public void deleteAddress(@RequestParam UUID uuid) {
        deliveryAddressService.deleteAddress(uuid);
    }
}
