package by.ita.chernook.dto.to_web;

import by.ita.chernook.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal balance;

    private User user;
    private List<DeliveryAddressWebDto> deliveryAddresses;
    private List<OrderWebDto> orders = new ArrayList<>();
    private CartWebDto cart;
}