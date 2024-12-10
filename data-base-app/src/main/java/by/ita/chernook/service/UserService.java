package by.ita.chernook.service;

import by.ita.chernook.model.Product;
import by.ita.chernook.model.User;
import by.ita.chernook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
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

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getUuid())) {
            throw new NoSuchElementException(String.format("User with UUID: %s not found", user.getUuid()));
        }
        return userRepository.save(user);
    }

    public User findUserById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("User with UUID: %s not found", uuid)));
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new NoSuchElementException(String.format("User with login: %s not found", login)));

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User deleteUser(UUID uuid) {
        User user = findUserById(uuid);
        userRepository.deleteById(uuid);
        return user;
    }
}