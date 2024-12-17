package by.ita.chernook;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_data_base.UserDatabaseDto;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.User;
import by.ita.chernook.service.UserService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends TestUtils {

    @Mock
    private UserMapper userMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_then_return_insertedUser() {
        UUID userUuid = UUID.randomUUID();
        User testUser = User.builder().uuid(userUuid).login("login").password("password").build();
        UserDatabaseDto testUserDatabaseDto = UserDatabaseDto.builder().uuid(userUuid).login("login").password("password").build();

        when(userMapper.toDatabaseDTO(testUser)).thenReturn(testUserDatabaseDto);
        when(userMapper.toEntity(restTemplate.postForObject(REQUEST_USER_CREATE, testUserDatabaseDto, UserDatabaseDto.class))).thenReturn(testUser);

        User actualUser = userService.createUser(testUser);

        assertEquals(testUser, actualUser);

        verify(userMapper, times(1)).toDatabaseDTO(testUser);
        verify(userMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_USER_CREATE, testUserDatabaseDto, UserDatabaseDto.class));
    }

    @Test
    void findUserById_then_return_existingUser() {
        UUID userUuid = UUID.randomUUID();
        User testUser = User.builder().uuid(UUID.randomUUID()).login("login").password("password").build();

        when(userMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_USER_READ, userUuid), UserDatabaseDto.class))).thenReturn(testUser);

        User actualUser = userService.findUserByLogin(testUser.getLogin());

        assertEquals(testUser, actualUser);

        verify(userMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_USER_READ, userUuid), UserDatabaseDto.class));
    }
}
