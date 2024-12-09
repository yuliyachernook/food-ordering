package by.ita.chernook.dto.to_data_base;

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
public class CartDatabaseDto {

    private UUID uuid;
    private CustomerDatabaseDto customer;
    private List<CartItemDatabaseDto> cartItems = new ArrayList<>();
}