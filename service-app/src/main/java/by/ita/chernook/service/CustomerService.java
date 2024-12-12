package by.ita.chernook.service;

import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.mapper.CustomerMapper;
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

    private static final String REQUEST_CREATE = "/customer/create";
    private static final String REQUEST_UPDATE = "/customer/update";
    private static final String REQUEST_READ = "/customer/read?uuid=%s";
    private static final String REQUEST_READ_BY_USER_UUID = "/customer/read/user/%s";
    private static final String REQUEST_READ_ALL = "/customer/read/all";

    private final RestTemplate restTemplate;
    private final CustomerMapper customerMapper;

    public Customer createCustomer(Customer customer) {
        customer.setCart(new Cart());
        customer.getUser().setUserRoleEnum(UserRoleEnum.CUSTOMER);
        CustomerDatabaseDto customerDatabaseDto = customerMapper.toDatabaseDTO(customer);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, customerDatabaseDto, CustomerDatabaseDto.class));
    }

    public Customer findCustomerById(UUID uuid) {
        String url = String.format(REQUEST_READ, uuid);
        return customerMapper.toEntity(restTemplate.getForObject(url, CustomerDatabaseDto.class));
    }

    public Customer findCustomerByUserId(UUID userUuid) {
        String url = String.format(REQUEST_READ_BY_USER_UUID, userUuid);
        return customerMapper.toEntity(restTemplate.getForObject(url, CustomerDatabaseDto.class));
    }

    public List<Customer> findAll() {
        ResponseEntity<List<CustomerDatabaseDto>> response = restTemplate.exchange(REQUEST_READ_ALL, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(customerMapper::toEntity)
                .collect(Collectors.toList());
    }

    public Customer addAddress(UUID customerId, DeliveryAddress deliveryAddress) {
        Customer customer = findCustomerById(customerId);
        customer.getDeliveryAddresses().add(deliveryAddress);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_UPDATE, customerMapper.toDatabaseDTO(customer), CustomerDatabaseDto.class));
    }
}
