package by.ita.chernook.service;

import by.ita.chernook.model.OrderItem;
import by.ita.chernook.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem insertOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        if (!orderItemRepository.existsById(orderItem.getUuid())) {
            throw new NoSuchElementException(String.format("OrderItem with UUID: %s not found", orderItem.getUuid()));
        }
        return orderItemRepository.save(orderItem);
    }

    public OrderItem deleteOrderItem(UUID uuid) {
        OrderItem cartItem = findOrderItemById(uuid);
        orderItemRepository.deleteById(uuid);
        return cartItem;
    }

    @Transactional
    public void deleteAllCartItemsByCartUuid(UUID orderUuid) {
        orderItemRepository.deleteById(orderUuid);
    }

    public OrderItem findOrderItemById(UUID uuid) {
        return orderItemRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("OrderItem with UUID: %s not found", uuid)));
    }

    public List<OrderItem> findAllByOrderUuid(UUID cartUuid) {
        return orderItemRepository.findAllByOrderUuid(cartUuid);
    }
}
