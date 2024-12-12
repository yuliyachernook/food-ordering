package by.ita.chernook.repository;

import by.ita.chernook.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCartUuid(UUID cartUuid);
    Optional<CartItem> findByCartUuidAndProductUuid(UUID cartUuid, UUID productUuid);
    void deleteByCartUuid(UUID cartUuid);
}
