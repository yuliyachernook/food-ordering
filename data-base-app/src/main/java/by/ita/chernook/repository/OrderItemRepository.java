package by.ita.chernook.repository;

import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
