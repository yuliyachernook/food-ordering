package by.ita.chernook.mapper;

import by.ita.chernook.dto.CustomerDto;
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

    public CustomerDto toDTO(Customer customer) {
        return CustomerDto.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .balance(customer.getBalance())
                .user(customer.getUser() != null ? customer.getUser() : null)
                .deliveryAddresses(customer.getDeliveryAddresses() != null ?
                        customer.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customer.getCart() != null ? cartMapper.toDTO(customer.getCart()) : null)
                .build();
    }

    public Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .uuid(customerDto.getUuid())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .phoneNumber(customerDto.getPhoneNumber())
                .email(customerDto.getEmail())
                .balance(customerDto.getBalance())
                .user(customerDto.getUser() != null ? customerDto.getUser() : null)
                .deliveryAddresses(customerDto.getDeliveryAddresses() != null ?
                        customerDto.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customerDto.getCart() != null ? cartMapper.toEntity(customerDto.getCart()) : null)
                .build();
    }
}
