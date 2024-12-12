package by.ita.chernook.dto;

import by.ita.chernook.dto.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserWebDto {


    private UUID uuid;
    private String login;
    private String password;
    private UserRoleEnum userRoleEnum;

}
