package by.ita.chernook.service;

import by.ita.chernook.model.DeliveryAddress;
import by.ita.chernook.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public DeliveryAddress insertDeliveryAddress(DeliveryAddress deliveryAddress) {
        return deliveryAddressRepository.save(deliveryAddress);
    }

    public DeliveryAddress updateDeliveryAddress(DeliveryAddress deliveryAddress) {
        if (!deliveryAddressRepository.existsById(deliveryAddress.getUuid())) {
            throw new NoSuchElementException(String.format("DeliveryAddress with UUID: %s not found", deliveryAddress.getUuid()));
        }
        return deliveryAddressRepository.save(deliveryAddress);
    }

    public DeliveryAddress findDeliveryAddressById(UUID uuid) {
        return deliveryAddressRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("DeliveryAddress with UUID: %s not found", uuid)));

    }
    public List<DeliveryAddress> findAll() {
        return deliveryAddressRepository.findAll();
    }

    public DeliveryAddress deleteDeliveryAddress(UUID uuid) {
        DeliveryAddress deliveryAddress = findDeliveryAddressById(uuid);
        deliveryAddressRepository.deleteById(uuid);
        return deliveryAddress;
    }
}
