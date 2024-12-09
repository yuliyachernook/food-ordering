package by.ita.chernook.dto.to_web;

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
public class CartWebDto {

    private UUID uuid;
    private CustomerWebDto customer;
    private List<CartItemWebDto> cartItems = new ArrayList<>();
}