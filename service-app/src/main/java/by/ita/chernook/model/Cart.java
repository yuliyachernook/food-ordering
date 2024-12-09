package by.ita.chernook.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Cart {

    private UUID uuid;
    @ToString.Exclude
    private Customer customer;
    private List<CartItem> cartItems = new ArrayList<>();
}