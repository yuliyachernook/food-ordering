package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.dto.to_web.CustomerWebDto;
import by.ita.chernook.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomerMapper {

    private final DeliveryAddressMapper deliveryAddressMapper;
    private final CartMapper cartMapper;

    public CustomerDatabaseDto toDatabaseDTO(Customer customer) {
        return CustomerDatabaseDto.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .balance(customer.getBalance())
                .user(customer.getUser() != null ? customer.getUser() : null)
                .deliveryAddresses(customer.getDeliveryAddresses() != null ?
                        customer.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toDatabaseDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customer.getCart() != null ? cartMapper.toDatabaseDTO(customer.getCart()) : null)
                .build();
    }

    public CustomerWebDto toWebDTO(Customer customer) {
        return CustomerWebDto.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .balance(customer.getBalance())
                .user(customer.getUser() != null ? customer.getUser() : null)
                .deliveryAddresses(customer.getDeliveryAddresses() != null ?
                        customer.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toWebDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customer.getCart() != null ? cartMapper.toWebDTO(customer.getCart()) : null)
                .build();
    }

    public Customer toEntity(CustomerDatabaseDto customerDatabaseDto) {
        return Customer.builder()
                .uuid(customerDatabaseDto.getUuid())
                .firstName(customerDatabaseDto.getFirstName())
                .lastName(customerDatabaseDto.getLastName())
                .phoneNumber(customerDatabaseDto.getPhoneNumber())
                .email(customerDatabaseDto.getEmail())
                .balance(customerDatabaseDto.getBalance())
                .user(customerDatabaseDto.getUser() != null ? customerDatabaseDto.getUser() : null)
                .deliveryAddresses(customerDatabaseDto.getDeliveryAddresses() != null ?
                        customerDatabaseDto.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customerDatabaseDto.getCart() != null ? cartMapper.toEntity(customerDatabaseDto.getCart()) : null)
                .build();
    }

    public Customer toEntity(CustomerWebDto customerWebDto) {
        return Customer.builder()
                .uuid(customerWebDto.getUuid())
                .firstName(customerWebDto.getFirstName())
                .lastName(customerWebDto.getLastName())
                .phoneNumber(customerWebDto.getPhoneNumber())
                .email(customerWebDto.getEmail())
                .balance(customerWebDto.getBalance())
                .user(customerWebDto.getUser() != null ? customerWebDto.getUser() : null)
                .deliveryAddresses(customerWebDto.getDeliveryAddresses() != null ?
                        customerWebDto.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customerWebDto.getCart() != null ? cartMapper.toEntity(customerWebDto.getCart()) : null)
                .build();
    }
}
