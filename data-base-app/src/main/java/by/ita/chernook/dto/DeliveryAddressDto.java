package by.ita.chernook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeliveryAddressDto {

    private UUID uuid;
    private String city;
    private String street;
    private String house;
    private String apartment;

    private CustomerDto customer;
}
