package by.ita.chernook.controller;

import by.ita.chernook.dto.ProductDto;
import by.ita.chernook.dto.UserDto;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.User;
import by.ita.chernook.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User insertedUser = userService.insertUser(user);
        return userMapper.toDTO(insertedUser);
    }

    @PutMapping("/update")
    public UserDto update(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(user);
        return userMapper.toDTO(updatedUser);
    }

    @GetMapping("/read")
    public UserDto read(@RequestParam UUID uuid) {
        User user = userService.findUserById(uuid);
        return userMapper.toDTO(user);
    }

    @GetMapping("/read/login/{login}")
    public UserDto read(@PathVariable String login) {
        User user = userService.findUserByLogin(login);
        return userMapper.toDTO(user);
    }

    @GetMapping("/read/all")
    public List<UserDto> readAll() {
        return userService.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public UserDto delete(@RequestParam UUID uuid) {
        User user = userService.deleteUser(uuid);
        return userMapper.toDTO(user);
    }
}

