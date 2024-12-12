package by.ita.chernook.model;

import by.ita.chernook.dto.enums.UserRoleEnum;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {

    private UUID uuid;
    private String login;
    private String password;
    private UserRoleEnum userRoleEnum;
    private ZonedDateTime creationDateTime;

    @ToString.Exclude
    private Customer customer;
}
