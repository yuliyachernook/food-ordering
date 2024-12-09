package by.ita.chernook.controller;

import by.ita.chernook.dto.DeliveryAddressDto;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.model.DeliveryAddress;
import by.ita.chernook.service.DeliveryAddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/database/delivery/address")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;
    private final DeliveryAddressMapper deliveryAddressMapper;

    @PostMapping("/create")
    public DeliveryAddressDto create(@RequestBody DeliveryAddressDto deliveryAddressDto) {
        DeliveryAddress deliveryAddress = deliveryAddressMapper.toEntity(deliveryAddressDto);
        DeliveryAddress insertedDeliveryAddress = deliveryAddressService.insertDeliveryAddress(deliveryAddress);
        return deliveryAddressMapper.toDTO(insertedDeliveryAddress);
    }

    @DeleteMapping("/delete/{id}")
    public DeliveryAddressDto delete(@PathVariable UUID id) {
        DeliveryAddress deletedProduct = deliveryAddressService.deleteDeliveryAddress(id);
        return deliveryAddressMapper.toDTO(deletedProduct);
    }
}
