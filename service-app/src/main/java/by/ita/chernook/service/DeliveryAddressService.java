package by.ita.chernook.service;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.dto.to_data_base.DeliveryAddressDatabaseDto;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.mapper.DeliveryAddressMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.DeliveryAddress;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private static final String REQUEST_READ_ALL = "/delivery/address/read/all";
    private static final String REQUEST_READ_ALL_BY_CATEGORY = "/product/read/all/category";
    private static final String REQUEST_CREATE = "/delivery/address/create";

    private final RestTemplate restTemplate;
    private final DeliveryAddressMapper deliveryAddressMapper;

    public DeliveryAddress createDeliveryAddress(DeliveryAddress deliveryAddress) {
        DeliveryAddressDatabaseDto deliveryAddressDatabaseDto = deliveryAddressMapper.toDatabaseDTO(deliveryAddress);

        return deliveryAddressMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, deliveryAddress, DeliveryAddressDatabaseDto.class));
    }
}
