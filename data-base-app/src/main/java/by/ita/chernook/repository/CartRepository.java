package by.ita.chernook.repository;

import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    Optional<Cart> findByCustomerUuid(UUID customerUuid);
}
