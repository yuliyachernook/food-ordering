package by.ita.chernook.mapper;

import by.ita.chernook.dto.UserDto;
import by.ita.chernook.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserDto toDTO(User user) {
        return UserDto.builder()
                .uuid(user.getUuid())
                .login(user.getLogin())
                .password(user.getPassword())
                .userRoleEnum(user.getUserRoleEnum())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .uuid(userDto.getUuid())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .userRoleEnum(userDto.getUserRoleEnum())
                .creationDateTime(ZonedDateTime.now())
                .build();
    }

}
