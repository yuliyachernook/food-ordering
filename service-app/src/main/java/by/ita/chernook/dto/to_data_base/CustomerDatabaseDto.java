package by.ita.chernook.dto.to_data_base;

import by.ita.chernook.dto.to_web.CouponWebDto;
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
public class CustomerDatabaseDto {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private BigDecimal balance;

    private User user;
    private List<DeliveryAddressDatabaseDto> deliveryAddresses;
    private List<OrderDatabaseDto> orders = new ArrayList<>();
    private CartDatabaseDto cart;
    private List<CouponDatabaseDto> coupons;
}