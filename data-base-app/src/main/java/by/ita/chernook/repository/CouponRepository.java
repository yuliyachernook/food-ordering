package by.ita.chernook.repository;

import by.ita.chernook.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByIsGlobal(boolean isGlobal);
}