package by.ita.chernook;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.repository.CouponRepository;
import by.ita.chernook.service.CouponService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest extends TestUtils {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    void insertCoupon_then_return() {
        Coupon testCoupon = buildCoupon(UUID.randomUUID(), "Test coupon", "Test desctiption", "12345", CouponTypeEnum.FIXED, 5, 5);

        when(couponRepository.save(testCoupon)).thenReturn(testCoupon);

        Coupon actualCoupon = couponService.createCoupon(testCoupon);

        assertEquals(testCoupon, actualCoupon);

        verify(couponRepository, times(1)).save(testCoupon);
    }

    @Test
    void updateCoupon_then_return() {
        UUID couponUuid = UUID.randomUUID();
        Coupon testCoupon = buildCoupon(couponUuid, "Test coupon", "Test desctiption", "12345", CouponTypeEnum.FIXED, 5, 5);

        when(couponRepository.existsById(couponUuid)).thenReturn(true);
        when(couponRepository.save(testCoupon)).thenReturn(testCoupon);

        Coupon actualCoupon = couponService.updateCoupon(testCoupon);

        assertEquals(testCoupon, actualCoupon);

        verify(couponRepository, times(1)).existsById(couponUuid);
        verify(couponRepository, times(1)).save(testCoupon);
    }

    @Test
    void updateCoupon_then_throws_NoSuchElementException() {
        UUID couponUuid = UUID.randomUUID();
        Coupon testCoupon = buildCoupon(couponUuid, "Test coupon", "Test desctiption", "12345", CouponTypeEnum.FIXED, 5, 5);

        when(couponRepository.existsById(couponUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> couponService.updateCoupon(testCoupon));

        verify(couponRepository, times(1)).existsById(couponUuid);
        verify(couponRepository, times(0)).save(testCoupon);
    }

    @Test
    void deleteCoupon_then_return() {
        UUID couponUuid = UUID.randomUUID();
        Coupon testCoupon = buildCoupon(couponUuid, "Test coupon", "Test desctiption", "12345", CouponTypeEnum.FIXED, 5, 5);

        when(couponRepository.findById(couponUuid)).thenReturn(Optional.of(testCoupon));
        doNothing().when(couponRepository).deleteById(couponUuid);

        Coupon actualCoupon = couponService.deleteCoupon(couponUuid);

        assertEquals(testCoupon, actualCoupon);

        verify(couponRepository, times(1)).findById(couponUuid);
        verify(couponRepository, times(1)).deleteById(couponUuid);
    }

    @Test
    void deleteCoupon_then_throws_NoSuchElementException() {
        UUID couponUuid = UUID.randomUUID();

        when(couponRepository.findById(couponUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> couponService.deleteCoupon(couponUuid));

        verify(couponRepository, times(1)).findById(couponUuid);
        verify(couponRepository, times(0)).deleteById(couponUuid);
    }

    @Test
    void findCouponById_then_return() {
        UUID couponUuid = UUID.randomUUID();
        Coupon testCoupon = buildCoupon(couponUuid, "Test coupon", "Test desctiption", "12345", CouponTypeEnum.FIXED, 5, 5);

        when(couponRepository.findById(couponUuid)).thenReturn(Optional.of(testCoupon));

        Coupon actualCoupon = couponService.findCouponById(couponUuid);

        assertEquals(testCoupon, actualCoupon);

        verify(couponRepository, times(1)).findById(couponUuid);
    }

    @Test
    void findCouponById_then_throws_NoSuchElementException() {
        UUID couponUuid = UUID.randomUUID();

        when(couponRepository.findById(couponUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> couponService.findCouponById(couponUuid));

        verify(couponRepository, times(1)).findById(couponUuid);
    }

    @Test
    void findCouponByCode_then_return() {
        String code = "123";
        Coupon testCoupon = buildCoupon(UUID.randomUUID(), "Test coupon", "Test desctiption", code, CouponTypeEnum.FIXED, 5, 5);

        when(couponRepository.findByCode(code)).thenReturn(Optional.of(testCoupon));

        Coupon actualCoupon = couponService.findCouponByCode(code);

        assertEquals(testCoupon, actualCoupon);

        verify(couponRepository, times(1)).findByCode(code);
    }

    @Test
    void findCouponByCode_then_throws_NoSuchElementException() {
        String code = "123";

        when(couponRepository.findByCode(code)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> couponService.findCouponByCode(code));

        verify(couponRepository, times(1)).findByCode(code);
    }

    @Test
    void findAlL_then_return() {
        Coupon testCoupon = buildCoupon(UUID.randomUUID(), "Test coupon", "Test desctiption", "12345", CouponTypeEnum.FIXED, 5, 5);
        Coupon testCoupon2 = buildCoupon(UUID.randomUUID(), "Test coupon", "Test desctiption", "54321", CouponTypeEnum.FIXED, 2, 10);
        List<Coupon> testList = new ArrayList<>(Arrays.asList(testCoupon, testCoupon2));

        when(couponRepository.findAll()).thenReturn(testList);

        List<Coupon> actualList = couponService.findAll();

        assertIterableEquals(testList, actualList);

        verify(couponRepository, times(1)).findAll();
    }
}
