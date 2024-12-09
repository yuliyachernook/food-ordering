package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.UserDatabaseDto;
import by.ita.chernook.dto.to_web.UserWebDto;
import by.ita.chernook.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserDatabaseDto toDatabaseDTO(User user) {
        return UserDatabaseDto.builder()
                .uuid(user.getUuid())
                .login(user.getLogin())
                .password(user.getPassword())
                .userRoleEnum(user.getUserRoleEnum())
                .build();
    }

    public UserWebDto toWebDTO(User user) {
        return UserWebDto.builder()
                .uuid(user.getUuid())
                .login(user.getLogin())
                .password(user.getPassword())
                .userRoleEnum(user.getUserRoleEnum())
                .build();
    }

    public User toEntity(UserDatabaseDto userDatabaseDto) {
        return User.builder()
                .uuid(userDatabaseDto.getUuid())
                .login(userDatabaseDto.getLogin())
                .password(userDatabaseDto.getPassword())
                .userRoleEnum(userDatabaseDto.getUserRoleEnum())
                .creationDateTime(ZonedDateTime.now())
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
