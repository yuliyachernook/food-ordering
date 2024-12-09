package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.DeliveryAddressDatabaseDto;
import by.ita.chernook.dto.to_web.DeliveryAddressWebDto;
import by.ita.chernook.model.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryAddressMapper {

    public DeliveryAddressDatabaseDto toDatabaseDTO(DeliveryAddress deliveryAddress) {
        return DeliveryAddressDatabaseDto.builder()
                .uuid(deliveryAddress.getUuid())
                .city(deliveryAddress.getCity())
                .house(deliveryAddress.getHouse())
                .street(deliveryAddress.getStreet())
                .apartment(deliveryAddress.getApartment())
                .build();
    }

    public DeliveryAddressWebDto toWebDTO(DeliveryAddress deliveryAddress) {
        return DeliveryAddressWebDto.builder()
                .uuid(deliveryAddress.getUuid())
                .city(deliveryAddress.getCity())
                .house(deliveryAddress.getHouse())
                .street(deliveryAddress.getStreet())
                .apartment(deliveryAddress.getApartment())
                .build();
    }

    public DeliveryAddress toEntity(DeliveryAddressDatabaseDto deliveryAddressDatabaseDto) {
        return DeliveryAddress.builder()
                .uuid(deliveryAddressDatabaseDto.getUuid())
                .city(deliveryAddressDatabaseDto.getCity())
                .house(deliveryAddressDatabaseDto.getHouse())
                .street(deliveryAddressDatabaseDto.getStreet())
                .apartment(deliveryAddressDatabaseDto.getApartment())
                .build();
    }

    public DeliveryAddress toEntity(DeliveryAddressWebDto deliveryAddressWebDto) {
        return DeliveryAddress.builder()
                .uuid(deliveryAddressWebDto.getUuid())
                .city(deliveryAddressWebDto.getCity())
                .house(deliveryAddressWebDto.getHouse())
                .street(deliveryAddressWebDto.getStreet())
                .apartment(deliveryAddressWebDto.getApartment())
                .build();
    }
}
