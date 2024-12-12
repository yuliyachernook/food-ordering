package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.UserDatabaseDto;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String REQUEST_CREATE = "/user/create";
    private static final String REQUEST_READ = "/user/read/login/%s";

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;

    public User findUserByLogin(String login) {
        return userMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, login), UserDatabaseDto.class));
    }

    public User createUser(User user) {
        UserDatabaseDto userDatabaseDto = userMapper.toDatabaseDTO(user);
        return userMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, userDatabaseDto, UserDatabaseDto.class));
    }
}
