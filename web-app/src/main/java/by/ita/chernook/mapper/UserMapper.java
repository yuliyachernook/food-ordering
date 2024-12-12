package by.ita.chernook.mapper;

import by.ita.chernook.dto.UserWebDto;
import by.ita.chernook.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserWebDto toWebDTO(User user) {
        return UserWebDto.builder()
                .uuid(user.getUuid())
                .login(user.getLogin())
                .password(user.getPassword())
                .userRoleEnum(user.getUserRoleEnum())
                .build();
    }

    public User toEntity(UserWebDto userWebDto) {
        return User.builder()
                .uuid(userWebDto.getUuid())
                .login(userWebDto.getLogin())
                .password(userWebDto.getPassword())
                .userRoleEnum(userWebDto.getUserRoleEnum())
                .creationDateTime(ZonedDateTime.now())
                .build();
    }
}
