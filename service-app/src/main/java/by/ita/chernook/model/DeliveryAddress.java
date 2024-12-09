package by.ita.chernook.model;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeliveryAddress {

    private UUID uuid;
    private String city;
    private String street;
    private String house;
    private String apartment;
    @ToString.Exclude
    private Customer customer;
}
