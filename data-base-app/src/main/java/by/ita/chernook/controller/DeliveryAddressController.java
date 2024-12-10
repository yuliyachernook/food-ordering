package by.ita.chernook.controller;

import by.ita.chernook.dto.DeliveryAddressDto;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.model.DeliveryAddress;
import by.ita.chernook.service.DeliveryAddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PutMapping("/update")
    public DeliveryAddressDto update(@RequestBody DeliveryAddressDto deliveryAddressDto) {
        DeliveryAddress deliveryAddress = deliveryAddressMapper.toEntity(deliveryAddressDto);
        DeliveryAddress updatedDeliveryAddress = deliveryAddressService.updateDeliveryAddress(deliveryAddress);
        return deliveryAddressMapper.toDTO(updatedDeliveryAddress);
    }

    @GetMapping("/read")
    public DeliveryAddressDto read(@RequestParam UUID uuid) {
        DeliveryAddress deliveryAddress = deliveryAddressService.findDeliveryAddressById(uuid);
        return deliveryAddressMapper.toDTO(deliveryAddress);
    }

    @GetMapping("/read/all")
    public List<DeliveryAddressDto> readAll() {
        return deliveryAddressService.findAll()
                .stream()
                .map(deliveryAddressMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public DeliveryAddressDto delete(@RequestParam UUID uuid) {
        DeliveryAddress deletedDeliveryAddress = deliveryAddressService.deleteDeliveryAddress(uuid);
        return deliveryAddressMapper.toDTO(deletedDeliveryAddress);
    }
}
