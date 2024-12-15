package by.ita.chernook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Customer {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private ZonedDateTime creationDateTime;
    private BigDecimal balance;

    private User user;
    private List<DeliveryAddress> deliveryAddresses;
    private List<Order> orders = new ArrayList<>();
    private Cart cart;
    private List<Coupon> coupons;
}