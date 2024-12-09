package by.ita.chernook.controller;

import by.ita.chernook.dto.UserDto;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.User;
import by.ita.chernook.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/read/login/{login}")
    public UserDto read(@PathVariable String login) {
        User user = userService.findUserByLogin(login);
        return userMapper.toDTO(user);
    }
}

