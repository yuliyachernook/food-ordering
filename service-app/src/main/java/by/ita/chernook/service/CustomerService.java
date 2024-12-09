package by.ita.chernook.service;

import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.dto.to_data_base.UserDatabaseDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final String REQUEST_READ_ALL = "/customer/read/all";
    static final String REQUEST_READ_BY_ID = "/customer/read?id={id}";
    private static final String REQUEST_READ_BY_USER_UUID = "/customer/read/user/";
    private static final String REQUEST_CREATE = "/customer/create";
    private static final String REQUEST_UPDATE = "/customer/update?id={id}";
    private static final String REQUEST_DELETE = "/delivery/address/delete/";

    private final RestTemplate restTemplate;
    private final CustomerMapper customerMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    public List<Customer> findAll() {
        ResponseEntity<List<CustomerDatabaseDto>> response = restTemplate.exchange(REQUEST_READ_ALL, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(customerMapper::toEntity)
                .collect(Collectors.toList());
    }

    public Customer findCustomerById(UUID id) {
        return customerMapper.toEntity(restTemplate.getForObject(REQUEST_READ_BY_ID, CustomerDatabaseDto.class, id));
    }

    public Customer findCustomerByUserId(UUID userUuid) {
        return customerMapper.toEntity(restTemplate.getForObject(REQUEST_READ_BY_USER_UUID +userUuid, CustomerDatabaseDto.class));
    }

    public Customer createCustomer(Customer customer) {
        customer.setCart(new Cart());
        customer.getUser().setUserRoleEnum(UserRoleEnum.CUSTOMER);
        CustomerDatabaseDto customerDatabaseDto = customerMapper.toDatabaseDTO(customer);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, customerDatabaseDto, CustomerDatabaseDto.class));
    }

    public Customer addAddress(UUID customerId, DeliveryAddress deliveryAddress) {
        Customer customer = findCustomerById(customerId);
        customer.getDeliveryAddresses().add(deliveryAddress);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_UPDATE, customerMapper.toDatabaseDTO(customer), CustomerDatabaseDto.class, customerId));
    }

    public void deleteAddress(UUID addressId) {
        restTemplate.delete(REQUEST_DELETE + addressId);
    }
}
