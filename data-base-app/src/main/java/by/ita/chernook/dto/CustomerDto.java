package by.ita.chernook.dto;

import by.ita.chernook.model.Coupon;
import by.ita.chernook.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerDto {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private User user;
    private List<DeliveryAddressDto> deliveryAddresses;
    private List<OrderDto> orders = new ArrayList<>();
    private CartDto cart;
    private List<CouponDto> coupons;
}