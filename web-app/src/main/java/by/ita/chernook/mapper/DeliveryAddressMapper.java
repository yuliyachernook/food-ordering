package by.ita.chernook.mapper;

import by.ita.chernook.dto.DeliveryAddressWebDto;
import by.ita.chernook.model.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeliveryAddressMapper {

    public DeliveryAddressWebDto toWebDTO(DeliveryAddress deliveryAddress) {
        return DeliveryAddressWebDto.builder()
                .uuid(deliveryAddress.getUuid())
                .city(deliveryAddress.getCity())
                .house(deliveryAddress.getHouse())
                .street(deliveryAddress.getStreet())
                .apartment(deliveryAddress.getApartment())
                .build();
    }

    public DeliveryAddress toEntity(DeliveryAddressWebDto deliveryAddressDto) {
        return DeliveryAddress.builder()
                .uuid(deliveryAddressDto.getUuid())
                .city(deliveryAddressDto.getCity())
                .house(deliveryAddressDto.getHouse())
                .street(deliveryAddressDto.getStreet())
                .apartment(deliveryAddressDto.getApartment())
                .build();
    }
}
