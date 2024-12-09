package by.ita.chernook.service;

import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.User;
import by.ita.chernook.repository.CustomerRepository;
import by.ita.chernook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User insertUser(User user) {
        user.setCreationDateTime(ZonedDateTime.now());
        return userRepository.save(user);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new NoSuchElementException(String.format("User with login: %s not found", login)));

    }
}