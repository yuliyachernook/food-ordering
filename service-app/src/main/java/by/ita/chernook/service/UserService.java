package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_data_base.UserDatabaseDto;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.Cart;
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
    private static final String REQUEST_READ = "/user/read/login/";
    private static final String REQUEST_CREATE = "/user/create";
    private static final String REQUEST_UPDATE = "/user/update?id={id}";
    private static final String REQUEST_DELETE = "/user/delete?id={id}";

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;


    public User findUserByLogin(String login) {
        return userMapper.toEntity(restTemplate.getForObject(REQUEST_READ + login, UserDatabaseDto.class));
    }

    public User createUser(User user) {
        UserDatabaseDto userDatabaseDto = userMapper.toDatabaseDTO(user);
        return userMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, userDatabaseDto, UserDatabaseDto.class));
    }
}
