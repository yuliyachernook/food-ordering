package by.ita.chernook.service;

import by.ita.chernook.model.Coupon;
import by.ita.chernook.model.Customer;
import by.ita.chernook.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    public List<Coupon> getGlobalCoupons() {
        return couponRepository.findByIsGlobal(true);
    }

    public Coupon findCouponById(UUID uuid) {
        return couponRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Coupon with id: %s not found", uuid)));

    }

    public Coupon findCouponByCode(String code) {
        return couponRepository.findByCode(code).orElseThrow(() ->
                new NoSuchElementException(String.format("Coupon with code: %s not found", code)));
    }
}