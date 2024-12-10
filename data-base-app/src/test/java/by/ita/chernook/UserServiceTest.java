package by.ita.chernook;

import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.model.User;
import by.ita.chernook.repository.UserRepository;
import by.ita.chernook.service.UserService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends TestUtils {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void insertUser_then_return() {
        User testUser = buildUser(UUID.randomUUID(), "login", "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(userRepository.save(testUser)).thenReturn(testUser);

        User actualUser = userService.insertUser(testUser);

        assertEquals(testUser, actualUser);

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void updateUser_then_return() {
        UUID userUuid = UUID.randomUUID();
        User testUser = buildUser(userUuid, "login", "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(userRepository.existsById(userUuid)).thenReturn(true);
        when(userRepository.save(testUser)).thenReturn(testUser);

        User actualUser = userService.updateUser(testUser);

        assertEquals(testUser, actualUser);

        verify(userRepository, times(1)).existsById(userUuid);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void updateUser_then_throws_NoSuchElementException() {
        UUID userUuid = UUID.randomUUID();
        User testUser = buildUser(userUuid, "login", "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(userRepository.existsById(userUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> userService.updateUser(testUser));

        verify(userRepository, times(1)).existsById(userUuid);
        verify(userRepository, times(0)).save(testUser);
    }

    @Test
    void deleteUser_then_return() {
        UUID userUuid = UUID.randomUUID();
        User testUser = buildUser(UUID.randomUUID(), "login", "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(userRepository.findById(userUuid)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById(userUuid);

        User actualUser = userService.deleteUser(userUuid);

        assertEquals(testUser, actualUser);

        verify(userRepository, times(1)).findById(userUuid);
        verify(userRepository, times(1)).deleteById(userUuid);
    }

    @Test
    void deleteUser_then_throws_NoSuchElementException() {
        UUID userUuid = UUID.randomUUID();

        when(userRepository.findById(userUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> userService.deleteUser(userUuid));

        verify(userRepository, times(1)).findById(userUuid);
        verify(userRepository, times(0)).deleteById(userUuid);
    }

    @Test
    void findUserById_then_return() {
        UUID userUuid = UUID.randomUUID();
        User testUser = buildUser(UUID.randomUUID(), "login", "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(userRepository.findById(userUuid)).thenReturn(Optional.of(testUser));

        User actualUser = userService.findUserById(userUuid);

        assertEquals(testUser, actualUser);

        verify(userRepository, times(1)).findById(userUuid);
    }

    @Test
    void findUserById_then_throws_NoSuchElementException() {
        UUID userUuid = UUID.randomUUID();

        when(userRepository.findById(userUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> userService.findUserById(userUuid));

        verify(userRepository, times(1)).findById(userUuid);
    }

    @Test
    void findUserByLogin_then_return() {
        String login = "login";
        User testUser = buildUser(UUID.randomUUID(), login, "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(testUser));

        User actualUser = userService.findUserByLogin(login);

        assertEquals(testUser, actualUser);

        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    void findUserByLogin_then_throws_NoSuchElementException() {
        String login = "login";

        when(userRepository.findByLogin(login)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> userService.findUserByLogin(login));

        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    void findAll_then_return() {
        User testUser = buildUser(UUID.randomUUID(), "login", "password", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        User testUser2 = buildUser(UUID.randomUUID(), "login2", "password2", UserRoleEnum.CUSTOMER,
                ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        List<User> testList = new ArrayList<>(Arrays.asList(testUser, testUser2));

        when(userService.findAll()).thenReturn(testList);

        List<User> actualList = userService.findAll();

        assertIterableEquals(testList, actualList);

        verify(userRepository, times(1)).findAll();
    }
}