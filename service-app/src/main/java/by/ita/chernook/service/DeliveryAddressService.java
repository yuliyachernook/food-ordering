package by.ita.chernook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private static final String REQUEST_DELETE = "/delivery/address/delete?uuid=%s";

    private final RestTemplate restTemplate;

    public void deleteAddress(UUID uuid) {
        restTemplate.delete(String.format(REQUEST_DELETE, uuid));
    }
}
