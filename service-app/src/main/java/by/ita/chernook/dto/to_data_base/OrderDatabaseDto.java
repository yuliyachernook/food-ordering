package by.ita.chernook.dto.to_data_base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderDatabaseDto {

    private UUID uuid;
    private CustomerDatabaseDto customer;
    private List<OrderItemDatabaseDto> orderItems;
    private DeliveryAddressDatabaseDto deliveryAddress;
    private double totalPrice;
    private String orderStatus;
    private ZonedDateTime creationDateTime;
    private String comment;
}