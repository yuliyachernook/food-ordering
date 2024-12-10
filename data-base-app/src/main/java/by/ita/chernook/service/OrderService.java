package by.ita.chernook.service;

import by.ita.chernook.model.Order;
import by.ita.chernook.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order insertOrder(Order order) {
        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
        }
        order.setCreationDateTime(ZonedDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        if (!orderRepository.existsById(order.getUuid())) {
            throw new NoSuchElementException(String.format("Order with UUID: %s not found", order.getUuid()));
        }
        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
        }
        return orderRepository.save(order);
    }

    public Order findOrderById(UUID uuid) {
        return orderRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Order with UUID: %s not found", uuid)));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByCustomerUuid(UUID customerUuid) {
        return orderRepository.findAllByCustomerUuid(customerUuid);
    }

    public Order deleteOrder(UUID uuid) {
        Order order = findOrderById(uuid);
        orderRepository.deleteById(uuid);
        return order;
    }
}
