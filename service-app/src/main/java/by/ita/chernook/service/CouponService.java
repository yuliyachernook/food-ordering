package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CouponDatabaseDto;
import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static by.ita.chernook.service.CustomerService.REQUEST_READ_BY_ID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final String REQUEST_CREATE = "/coupon/create";
    private static final String REQUEST_CUSTOMER_UPDATE = "/customer/update";


    private final RestTemplate restTemplate;
    private final CouponMapper couponMapper;
    private final CustomerMapper customerMapper;

    public Coupon createCoupon(Coupon coupon) {
        CouponDatabaseDto couponDatabaseDto = couponMapper.toDatabaseDTO(coupon);
        return couponMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, couponDatabaseDto, CouponDatabaseDto.class));
    }

    public Customer addCouponToCustomer(UUID id, Coupon coupon) {
        Coupon created = createCoupon(coupon);
        Customer customer = customerMapper.toEntity(restTemplate.getForObject(REQUEST_READ_BY_ID, CustomerDatabaseDto.class, id));
        customer.getCoupons().add(created);
        return customerMapper.toEntity(restTemplate.postForObject(REQUEST_CUSTOMER_UPDATE, customerMapper.toDatabaseDTO(customer), CustomerDatabaseDto.class));
    }
}
