package by.ita.chernook.controller;


import by.ita.chernook.dto.to_web.CartWebDto;
import by.ita.chernook.dto.to_web.ProductWebDto;
import by.ita.chernook.dto.to_web.UserWebDto;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.mapper.UserMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Product;
import by.ita.chernook.model.User;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/business/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public UserWebDto create(@RequestBody UserWebDto userWebDto) {
        User user = userMapper.toEntity(userWebDto);
        User insertedUser = userService.createUser(user);
        return userMapper.toWebDTO(insertedUser);
    }

    @GetMapping("/read/login/{login}")
    public UserWebDto read(@PathVariable String login) {
        User user = userService.findUserByLogin(login);
        return userMapper.toWebDTO(user);
    }
}
