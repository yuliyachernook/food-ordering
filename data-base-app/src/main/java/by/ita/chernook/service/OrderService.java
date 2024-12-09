package by.ita.chernook.service;

import by.ita.chernook.model.Order;
import by.ita.chernook.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order insertOrder(Order order) {
        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));
        }
        return orderRepository.save(order);
    }

}
