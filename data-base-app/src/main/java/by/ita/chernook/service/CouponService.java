package by.ita.chernook.service;

import by.ita.chernook.model.Coupon;
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

    public Coupon updateCoupon(Coupon coupon) {
        if (!couponRepository.existsById(coupon.getUuid())) {
            throw new NoSuchElementException(String.format("Coupon with UUID: %s not found", coupon.getUuid()));
        }
        return couponRepository.save(coupon);
    }

    public Coupon findCouponById(UUID uuid) {
        return couponRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Coupon with UUID: %s not found", uuid)));

    }

    public Coupon findCouponByCode(String code) {
        return couponRepository.findByCode(code).orElseThrow(() ->
                new NoSuchElementException(String.format("Coupon with code: %s not found", code)));
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    public Coupon deleteCoupon(UUID uuid) {
        Coupon coupon = findCouponById(uuid);
        couponRepository.deleteById(uuid);
        return coupon;
    }

}