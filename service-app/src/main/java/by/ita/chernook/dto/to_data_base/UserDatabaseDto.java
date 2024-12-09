package by.ita.chernook.dto.to_data_base;

import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDatabaseDto {

    private UUID uuid;
    private String login;
    private String password;
    private UserRoleEnum userRoleEnum;

}
