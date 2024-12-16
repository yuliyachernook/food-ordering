package by.ita.chernook.service;

import by.ita.chernook.dto.OrderWebDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final String REQUEST_CREATE_ORDER = "/order/create?";
    private static final String REQUEST_BUILD_ORDER = "/order/prepare?";
    private static final String REQUEST_UPDATE_ORDER = "/order/update";
    private static final String REQUEST_READ = "/order/read?uuid=%s";
    private static final String REQUEST_READ_ALL = "/order/read/all";
    private static final String REQUEST_READ_ALL_BY_CUSTOMER_UUID = "/order/read/all/customer/%s";

    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    public Order buildOrderForCustomer(UUID customerUuid) {
        return orderMapper.toEntity(restTemplate.postForObject(REQUEST_BUILD_ORDER + "customerUuid=" + customerUuid, null, OrderWebDto.class));
    }

    public Order createOrderForCustomer(Order order, UUID customerUuid, BigDecimal discountedTotalPrice) {
        if (discountedTotalPrice != null) {
            order.setTotalPrice(discountedTotalPrice);
        }
        return orderMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE_ORDER + "customerUuid=" + customerUuid, orderMapper.toWebDTO(order), OrderWebDto.class));
    }

    public Order updateOrder(Order order) {
        return orderMapper.toEntity(restTemplate.postForObject(REQUEST_UPDATE_ORDER, orderMapper.toWebDTO(order), OrderWebDto.class));
    }

    public Order readOrderById(String uuid) {
        return orderMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, uuid), OrderWebDto.class));
    }

    public List<Order> readAllOrders() {
        ResponseEntity<List<OrderWebDto>> response = restTemplate.exchange(REQUEST_READ_ALL, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderWebDto>>(){});
        return response.getBody().stream()
                .map(orderMapper::toEntity)
                .collect(Collectors.toList());
    }

    public List<Order> readAllOrdersByCustomerUuid(UUID customerUuid) {
        ResponseEntity<List<OrderWebDto>> response = restTemplate.exchange(String.format(REQUEST_READ_ALL_BY_CUSTOMER_UUID, customerUuid), HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderWebDto>>(){});
        return response.getBody().stream()
                .map(orderMapper::toEntity)
                .collect(Collectors.toList());
    }
}
