package by.ita.chernook;

import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.model.User;
import by.ita.chernook.service.*;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT extends TestUtils {

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(userService);
    }

    @Test
    public void createUser_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        User expectedUser = buildUser(UUID.randomUUID(), login, "password", UserRoleEnum.CUSTOMER, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        User actualUser = userService.createUser(expectedUser);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getUserRoleEnum(), actualUser.getUserRoleEnum());
    }

    @Test
    public void findUserByLogin_then_returnCorrect() {
        String login = String.valueOf(System.currentTimeMillis());
        User user = buildUser(UUID.randomUUID(), login, "password", UserRoleEnum.CUSTOMER, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        User createdUser = userService.createUser(user);

        User actualUser = userService.findUserByLogin(login);

        assertNotNull(actualUser);
        assertEquals(createdUser.getLogin(), actualUser.getLogin());
        assertEquals(createdUser.getPassword(), actualUser.getPassword());
        assertEquals(createdUser.getUserRoleEnum(), actualUser.getUserRoleEnum());
    }

}
