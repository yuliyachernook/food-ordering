package by.ita.chernook.dto.to_data_base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DeliveryAddressDatabaseDto {

    private UUID uuid;
    private String city;
    private String street;
    private String house;
    private String apartment;

    private CustomerDatabaseDto customer;
}
