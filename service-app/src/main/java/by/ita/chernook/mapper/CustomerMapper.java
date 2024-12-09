package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.CouponDatabaseDto;
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
    private final CouponMapper couponMapper;

    public CustomerDatabaseDto toDatabaseDTO(Customer customer) {
        return CustomerDatabaseDto.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .user(customer.getUser() != null ? customer.getUser() : null)
                .deliveryAddresses(customer.getDeliveryAddresses() != null ?
                        customer.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toDatabaseDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customer.getCart() != null ? cartMapper.toDatabaseDTO(customer.getCart()) : null)
                .coupons(customer.getCoupons() != null ?
                        customer.getCoupons().stream()
                                .map(couponMapper::toDatabaseDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    public CustomerWebDto toWebDTO(Customer customer) {
        return CustomerWebDto.builder()
                .uuid(customer.getUuid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .user(customer.getUser() != null ? customer.getUser() : null)
                .deliveryAddresses(customer.getDeliveryAddresses() != null ?
                        customer.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toWebDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customer.getCart() != null ? cartMapper.toWebDTO(customer.getCart()) : null)
                .coupons(customer.getCoupons() != null ?
                        customer.getCoupons().stream()
                                .map(couponMapper::toWebDTO)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    public Customer toEntity(CustomerDatabaseDto customerDatabaseDto) {
        return Customer.builder()
                .uuid(customerDatabaseDto.getUuid())
                .firstName(customerDatabaseDto.getFirstName())
                .lastName(customerDatabaseDto.getLastName())
                .phoneNumber(customerDatabaseDto.getPhoneNumber())
                .email(customerDatabaseDto.getEmail())
                .user(customerDatabaseDto.getUser() != null ? customerDatabaseDto.getUser() : null)
                .deliveryAddresses(customerDatabaseDto.getDeliveryAddresses() != null ?
                        customerDatabaseDto.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customerDatabaseDto.getCart() != null ? cartMapper.toEntity(customerDatabaseDto.getCart()) : null)
                .coupons(customerDatabaseDto.getCoupons() != null ?
                        customerDatabaseDto.getCoupons().stream()
                                .map(couponMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    public Customer toEntity(CustomerWebDto customerWebDto) {
        return Customer.builder()
                .uuid(customerWebDto.getUuid())
                .firstName(customerWebDto.getFirstName())
                .lastName(customerWebDto.getLastName())
                .phoneNumber(customerWebDto.getPhoneNumber())
                .email(customerWebDto.getEmail())
                .user(customerWebDto.getUser() != null ? customerWebDto.getUser() : null)
                .deliveryAddresses(customerWebDto.getDeliveryAddresses() != null ?
                        customerWebDto.getDeliveryAddresses().stream()
                                .map(deliveryAddressMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .cart(customerWebDto.getCart() != null ? cartMapper.toEntity(customerWebDto.getCart()) : null)
                .coupons(customerWebDto.getCoupons() != null ?
                        customerWebDto.getCoupons().stream()
                                .map(couponMapper::toEntity)
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }
}
