package by.ita.chernook.dto;

import by.ita.chernook.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerWebDto {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private double balance;

    private User user;
    private List<DeliveryAddressWebDto> deliveryAddresses;
    private List<OrderWebDto> orders = new ArrayList<>();
    @ToString.Exclude
    private CartWebDto cart;
}