package by.ita.chernook.repository;

import by.ita.chernook.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCartUuid(UUID cartUuid);
    CartItem findByCartUuidAndProductUuid(UUID cartUuid, UUID productUuid);
}
