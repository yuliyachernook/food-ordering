package by.ita.chernook.service;

import by.ita.chernook.dto.CouponWebDto;
import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final String REQUEST_CREATE = "/coupon/create";
    private static final String REQUEST_UPDATE = "/coupon/update";
    private static final String REQUEST_READ = "/coupon/read?uuid=%s";
    private static final String REQUEST_DELETE = "/coupon/delete?uuid=%s";
    private static final String REQUEST_APPLY = "/coupon/apply?coupon={coupon}";
    private static final String REQUEST_READ_BY_CODE = "/coupon/read/code?";
    private static final String REQUEST_READ_ALL_GLOBAL = "/coupon/read/all";


    private final RestTemplate restTemplate;
    private final CouponMapper couponMapper;

    public Coupon createCoupon(Coupon coupon) {
        return couponMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, couponMapper.toWebDTO(coupon), CouponWebDto.class));
    }

    public Coupon applyCoupon(String code) {
        try {
            return couponMapper.toEntity(restTemplate.postForObject(REQUEST_APPLY, null, CouponWebDto.class, code));
        } catch (Exception e) {
            return null;
        }
    }

    public Coupon findCouponByCode(String code) {
        try {
           return couponMapper.toEntity(restTemplate.getForObject(REQUEST_READ_BY_CODE + "code=" + code, CouponWebDto.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<Coupon> readAllCoupons() {
        ResponseEntity<List<CouponWebDto>> response = restTemplate.exchange(REQUEST_READ_ALL_GLOBAL, HttpMethod.GET, null, new ParameterizedTypeReference<List<CouponWebDto>>(){});
        return response.getBody().stream()
                .map(couponMapper::toEntity)
                .collect(Collectors.toList());
    }

    public Coupon readCouponById(String uuid) {
        return couponMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, uuid), CouponWebDto.class));
    }

    public Coupon updateCoupon(Coupon coupon) {
        restTemplate.put(REQUEST_UPDATE, couponMapper.toWebDTO(coupon), CouponWebDto.class);
        return coupon;
    }

    public void deleteCouponById(String uuid) {
        restTemplate.delete(String.format(REQUEST_DELETE, uuid));
    }
}
