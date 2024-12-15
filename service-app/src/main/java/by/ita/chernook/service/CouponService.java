package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CouponDatabaseDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final String REQUEST_CREATE = "/coupon/create";
    private static final String REQUEST_UPDATE = "/coupon/update";
    private static final String REQUEST_READ = "/coupon/read?uuid=%s";
    private static final String REQUEST_DELETE = "/coupon/delete?uuid=%s";
    private static final String REQUEST_READ_BY_CODE = "/coupon/read/code?code=%s";
    private static final String REQUEST_READ_GLOBAL = "/coupon/read/all";

    private final RestTemplate restTemplate;
    private final CouponMapper couponMapper;

    public Coupon createCoupon(Coupon coupon)  {
        CouponDatabaseDto couponDatabaseDto = couponMapper.toDatabaseDTO(coupon);
        return couponMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, couponDatabaseDto, CouponDatabaseDto.class));
    }

    public Coupon updateCoupon(Coupon coupon) {
        CouponDatabaseDto couponDatabaseDto = couponMapper.toDatabaseDTO(coupon);
        restTemplate.put(REQUEST_UPDATE, couponDatabaseDto);
        return findCouponById(coupon.getUuid());
    }

    public Coupon findCouponById(UUID uuid) {
        return couponMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, uuid), CouponDatabaseDto.class));
    }

    public void deleteCoupon(UUID uuid) {
        restTemplate.delete(String.format(REQUEST_DELETE, uuid));
    }

    public Coupon applyCoupon(String code)  {
        Coupon coupon = findCouponByCode(code);
        int couponAvailableUses = coupon.getAvailableUses();
        if (couponAvailableUses > 0) {
            coupon.setAvailableUses(couponAvailableUses - 1);
            CouponDatabaseDto couponDatabaseDto = couponMapper.toDatabaseDTO(coupon);
            restTemplate.put(REQUEST_UPDATE, couponDatabaseDto, CouponDatabaseDto.class);
           return coupon;
        } else throw new IllegalStateException(String.format("Coupon %s is not available", code));
    }

    public Coupon findCouponByCode(String code) {
        String url = String.format(REQUEST_READ_BY_CODE, code);
        CouponDatabaseDto couponDatabaseDto = restTemplate.getForObject(url, CouponDatabaseDto.class);
        if (couponDatabaseDto != null) {
            return couponMapper.toEntity(couponDatabaseDto);
        } else {
            return null;
        }
    }

    public List<Coupon> findAllGlobalCoupons() {
        ResponseEntity<List<CouponDatabaseDto>> response = restTemplate.exchange(REQUEST_READ_GLOBAL, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(couponMapper::toEntity)
                .collect(Collectors.toList());
    }
}
