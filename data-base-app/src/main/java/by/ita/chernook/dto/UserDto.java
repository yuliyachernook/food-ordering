package by.ita.chernook.dto;

import by.ita.chernook.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {


    private UUID uuid;
    private String login;
    private String password;

    private Customer customer;
}
