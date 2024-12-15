package by.ita.chernook.service;

import by.ita.chernook.dto.CustomerWebDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final String REQUEST_READ_ALL = "/customer/read/all";
    private static final String REQUEST_READ = "/customer/read";
    private static final String REQUEST_CREATE = "/customer/create";
    private static final String REQUEST_UPDATE = "/customer/update";

    private static final String REQUEST_ADD_ADDRESS = "/customer/add/address?";
    private static final String REQUEST_RECHARGE_BALANCE = "/customer/recharge/balance?";

    private final CustomerMapper customerMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public Customer createCustomer(Customer customer) {
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));

        CustomerWebDto customerWebDto = customerMapper.toWebDTO(customer);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, customerWebDto, CustomerWebDto.class));
    }

    public Customer updateCustomer(Customer customer) {
        CustomerWebDto customerWebDto = customerMapper.toWebDTO(customer);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_UPDATE, customerWebDto, CustomerWebDto.class));
    }

    public Customer addDeliveryAddress(UUID customerUuid, DeliveryAddress deliveryAddress) {
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_ADD_ADDRESS + "uuid=" + customerUuid, deliveryAddressMapper.toWebDTO(deliveryAddress), CustomerWebDto.class));
    }

    public Customer rechargeBalance(UUID customerUuid) {
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_RECHARGE_BALANCE + "uuid=" + customerUuid, null, CustomerWebDto.class));
    }
}
