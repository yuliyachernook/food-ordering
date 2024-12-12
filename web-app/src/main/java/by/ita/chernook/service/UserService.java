package by.ita.chernook.service;

import by.ita.chernook.dto.CustomerWebDto;
import by.ita.chernook.dto.UserWebDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String REQUEST_READ_ALL = "/user/read/all";
    private static final String REQUEST_READ_USER_BY_LOGIN = "/user/read/login/";
    private static final String REQUEST_READ_CUSTOMER_BY_ID = "/customer/read?uuid={uuid}";
    private static final String REQUEST_READ_CUSTOMER_BY_USER_UUID = "/customer/read/user/";

    private static final String REQUEST_CREATE = "/user/create";

    private final UserMapper userMapper;
    private final RestTemplate restTemplate;
    private final CustomerMapper customerMapper;


    public User createUser(User user) {
        UserWebDto userWebDto = userMapper.toWebDTO(user);
        return userMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, userWebDto, UserWebDto.class));
    }

    public User findUserByLogin(String login) {
        return userMapper.toEntity(restTemplate.getForObject(REQUEST_READ_USER_BY_LOGIN + login, UserWebDto.class));
    }

    public Customer findCustomerByUserId(UUID id) {
        return customerMapper.toEntity(restTemplate.getForObject(REQUEST_READ_CUSTOMER_BY_USER_UUID + id, CustomerWebDto.class));
    }

    public Customer findCustomerById(UUID id) {
        return customerMapper.toEntity(restTemplate.getForObject(REQUEST_READ_CUSTOMER_BY_ID, CustomerWebDto.class, id));
    }

}
