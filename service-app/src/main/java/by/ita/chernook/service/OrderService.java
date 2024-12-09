package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.OrderDatabaseDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {

    private static final String REQUEST_CREATE_ORDER = "/order/create";

    private final RestTemplate restTemplate;
    private final CustomerService customerService;
    private final CartItemService cartItemService;
    private final OrderMapper orderMapper;
    public Order createOrderFromCart(UUID customerUuid) {
        Customer customer = customerService.findCustomerById(customerUuid);

        List<CartItem> cartItems = cartItemService.findAllCartItemsByCartUuid(customer.getCart().getUuid());

        Order order = new Order();
        order.setCustomer(customer);
       // order.setDeliveryAddress(new DeliveryAddress());
        order.setCreationDateTime(ZonedDateTime.now());
        //order.setOrderStatus("NEW");

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        double totalPrice = orderItems.stream()
                    .mapToDouble(OrderItem::getPrice)
                    .sum();
        order.setTotalPrice(totalPrice);

        return orderMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE_ORDER, orderMapper.toDatabaseDTO(order),OrderDatabaseDto.class));
    }
}
