package by.ita.chernook.controller;

import by.ita.chernook.service.DeliveryAddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/business/delivery/address")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @DeleteMapping("/delete")
    public void deleteAddress(@RequestParam UUID uuid) {
        deliveryAddressService.deleteAddress(uuid);
    }
}
