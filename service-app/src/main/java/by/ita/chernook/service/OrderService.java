package by.ita.chernook.service;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.dto.enums.OrderStatusEnum;
import by.ita.chernook.dto.to_data_base.OrderDatabaseDto;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {

    private static final String REQUEST_CREATE_ORDER = "/order/create";
    private static final String REQUEST_UPDATE_ORDER = "/order/update";
    private static final String REQUEST_READ = "/order/read?uuid=%s";
    private static final String REQUEST_READ_ALL = "/order/read/all";
    private static final String REQUEST_READ_ALL_BY_CUSTOMER_UUID = "/order/read/all/customer/%s";

    private final RestTemplate restTemplate;
    private final CustomerService customerService;
    private final CartItemService cartItemService;
    private final CouponService couponService;

    private final OrderMapper orderMapper;

    public Order createOrder(Order order, UUID customerUuid) {
        Customer customer = customerService.findCustomerById(customerUuid);
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatusEnum.NEW);
        OrderDatabaseDto orderDatabaseDto = orderMapper.toDatabaseDTO(order);
        return orderMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE_ORDER, orderDatabaseDto, OrderDatabaseDto.class));
    }

    public Order updateOrder(Order order) {
        Order existingOrder = findOrderById(order.getUuid());
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setComment(order.getComment());
        OrderDatabaseDto orderDatabaseDto = orderMapper.toDatabaseDTO(order);
        restTemplate.put(REQUEST_UPDATE_ORDER, orderDatabaseDto, OrderDatabaseDto.class);
        return findOrderById(order.getUuid());
    }

    public Order findOrderById(UUID uuid) {
        return orderMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, uuid), OrderDatabaseDto.class));
    }

    public Order buildOrderFromCart(UUID customerUuid) {
        Customer customer = customerService.findCustomerById(customerUuid);
        List<CartItem> cartItems = cartItemService.findAllCartItemsByCartUuid(customer.getCart().getUuid());

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    double price = product.getPrice();
                    double discountPercentage = product.getDiscountPercentage();
                    if (discountPercentage > 0) {
                        price -= price * (discountPercentage / 100);
                    }
                    return OrderItem.builder()
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .price(price)
                            .build();
                })
                .toList();

        double totalPrice = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum();

        return Order.builder().customer(customer).orderItems(orderItems).totalPrice(totalPrice).build();
    }

    public List<Order> findAll() {
        ResponseEntity<List<OrderDatabaseDto>> response = restTemplate.exchange(REQUEST_READ_ALL, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(orderMapper::toEntity)
                .collect(Collectors.toList());
    }

    public List<Order> findAllByCustomerUuid(UUID customerUuid) {
        ResponseEntity<List<OrderDatabaseDto>> response = restTemplate.exchange(String.format(REQUEST_READ_ALL_BY_CUSTOMER_UUID, customerUuid), HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(orderMapper::toEntity)
                .collect(Collectors.toList());
    }
}
