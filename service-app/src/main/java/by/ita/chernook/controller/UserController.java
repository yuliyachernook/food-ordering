package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.UserWebDto;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.User;
import by.ita.chernook.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/business/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public UserWebDto create(@RequestBody UserWebDto userWebDto) {
        User insertedUser = userService.createUser(userMapper.toEntity(userWebDto));
        return userMapper.toWebDTO(insertedUser);
    }

    @GetMapping("/read/login/{login}")
    public UserWebDto read(@PathVariable String login) {
        User user = userService.findUserByLogin(login);
        return userMapper.toWebDTO(user);
    }
}
