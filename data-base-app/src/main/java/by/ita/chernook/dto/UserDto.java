package by.ita.chernook.dto;

import by.ita.chernook.dto.enums.UserRoleEnum;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {

    private UUID uuid;
    private String login;
    private String password;
    private UserRoleEnum userRoleEnum;
}
