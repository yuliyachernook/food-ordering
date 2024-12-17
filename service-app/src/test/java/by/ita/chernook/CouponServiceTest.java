package by.ita.chernook;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.dto.to_data_base.CouponDatabaseDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.service.CouponService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest extends TestUtils {

    @Mock
    private CouponMapper couponMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CouponService couponService;

    @Test
    void createCoupon_then_return_insertedCoupon() {
        UUID couponUuid = UUID.randomUUID();
        Coupon testCoupon = Coupon.builder().uuid(couponUuid).name("Test").description("Description").code("123").couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        CouponDatabaseDto testCouponDatabaseDto = CouponDatabaseDto.builder().uuid(couponUuid).name("Test").description("Description").code("123").couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();

        when(couponMapper.toDatabaseDTO(testCoupon)).thenReturn(testCouponDatabaseDto);
        when(couponMapper.toEntity(restTemplate.postForObject(REQUEST_COUPON_CREATE, testCouponDatabaseDto, CouponDatabaseDto.class))).thenReturn(testCoupon);

        Coupon actualCoupon = couponService.createCoupon(testCoupon);

        assertEquals(testCoupon, actualCoupon);

        verify(couponMapper, times(1)).toDatabaseDTO(testCoupon);
        verify(couponMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_COUPON_CREATE, testCouponDatabaseDto, CouponDatabaseDto.class));
    }

    @Test
    void updateCoupon_then_return_updatedCoupon() {
        UUID couponUuid = UUID.randomUUID();
        Coupon testCoupon = Coupon.builder().uuid(couponUuid).name("Test").description("Description").code("123").couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        CouponDatabaseDto testCouponDatabaseDto = CouponDatabaseDto.builder().uuid(couponUuid).name("Test").description("Description").code("123").couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();

        when(couponMapper.toDatabaseDTO(testCoupon)).thenReturn(testCouponDatabaseDto);
        when(couponMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_COUPON_READ, testCoupon.getUuid()), CouponDatabaseDto.class))).thenReturn(testCoupon);

        Coupon actualCoupon= couponService.updateCoupon(testCoupon);

        assertEquals(testCoupon, actualCoupon);

        verify(couponMapper, times(1)).toDatabaseDTO(testCoupon);
        verify(couponMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_COUPON_READ, testCoupon.getUuid()), CouponDatabaseDto.class));
    }

    @Test
    void applyCoupon_whenCouponIsAvailable_then_return_updatedCoupon() {
        UUID couponUuid = UUID.randomUUID();
        String couponCode = "123";
        Coupon testCoupon = Coupon.builder().uuid(couponUuid).name("Test").description("Description").code(couponCode).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(1).build();
        CouponDatabaseDto testCouponDatabaseDto = CouponDatabaseDto.builder().uuid(couponUuid).name("Test").description("Description").code(couponCode).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(1).build();

        String url = String.format(REQUEST_COUPON_READ_BY_CODE, couponCode);
        when(restTemplate.getForObject(url, CouponDatabaseDto.class)).thenReturn(testCouponDatabaseDto);
        when(couponMapper.toDatabaseDTO(testCoupon)).thenReturn(testCouponDatabaseDto);
        when(couponMapper.toEntity(testCouponDatabaseDto)).thenReturn(testCoupon);

        Coupon actualCoupon = couponService.applyCoupon(couponCode);

        assertEquals(testCoupon, actualCoupon);
        assertEquals(0, testCoupon.getAvailableUses());

        verify(restTemplate, times(1)).getForObject(url, CouponDatabaseDto.class);
        verify(couponMapper, times(1)).toDatabaseDTO(testCoupon);
        verify(couponMapper, times(1)).toEntity(testCouponDatabaseDto);
    }

    @Test
    void applyCoupon_whenCouponIsNotAvailable_then_throw_IllegalStateException() {
        UUID couponUuid = UUID.randomUUID();
        String couponCode = "123";
        Coupon testCoupon = Coupon.builder().uuid(couponUuid).name("Test").description("Description").code(couponCode).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(0).build();
        CouponDatabaseDto testCouponDatabaseDto = CouponDatabaseDto.builder().uuid(couponUuid).name("Test").description("Description").code(couponCode).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(0).build();

        String url = String.format(REQUEST_COUPON_READ_BY_CODE, couponCode);
        when(restTemplate.getForObject(url, CouponDatabaseDto.class)).thenReturn(testCouponDatabaseDto);
        when(couponMapper.toEntity(testCouponDatabaseDto)).thenReturn(testCoupon);

        assertThrows(IllegalStateException.class, () -> couponService.applyCoupon(couponCode));

        verify(restTemplate, times(1)).getForObject(url, CouponDatabaseDto.class);
        verify(couponMapper, times(1)).toEntity(testCouponDatabaseDto);
    }

    @Test
    void deleteCoupon() {
        UUID uuid = UUID.randomUUID();
        couponService.deleteCoupon(uuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_COUPON_DELETE, uuid));
    }
}
