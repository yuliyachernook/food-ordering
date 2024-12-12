package by.ita.chernook.dto.to_web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeliveryAddressWebDto {

    private UUID uuid;
    private String city;
    private String street;
    private String house;
    private Integer apartment;

    private CustomerWebDto customer;
}
