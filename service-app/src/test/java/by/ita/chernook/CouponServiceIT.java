package by.ita.chernook;

import by.ita.chernook.dto.enums.CouponTypeEnum;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.service.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CouponServiceIT {

    @Autowired
    private CouponService couponService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(couponService);
    }

    @Test
    public void createCoupon_then_returnCorrect() {
        Coupon expectedCoupon = Coupon.builder().name("Test").description("Description").code(String.valueOf(System.currentTimeMillis())).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();

        Coupon actualCoupon = couponService.createCoupon(expectedCoupon);

        assertNotNull(actualCoupon);
        assertEquals(expectedCoupon.getName(), actualCoupon.getName());
        assertEquals(expectedCoupon.getDescription(), actualCoupon.getDescription());
        assertEquals(expectedCoupon.getCode(), actualCoupon.getCode());
        assertEquals(expectedCoupon.getCouponType(), actualCoupon.getCouponType());
        assertEquals(expectedCoupon.getDiscount(), actualCoupon.getDiscount());
        assertEquals(expectedCoupon.getAvailableUses(), actualCoupon.getAvailableUses());
    }

    @Test
    public void updateCoupon_then_returnCorrect() {
        Coupon coupon = Coupon.builder().name("Test")
                .description("Description").code(String.valueOf(System.currentTimeMillis())).couponType(CouponTypeEnum.FIXED).discount(5).availableUses(5).build();
        Coupon createdCoupon = couponService.createCoupon(coupon);
        Coupon expectedCoupon = Coupon.builder().uuid(createdCoupon.getUuid()).name("Test").description("Updated Description").code("222")
                .couponType(CouponTypeEnum.FIXED).discount(10).availableUses(5).build();

        Coupon actualCoupon = couponService.updateCoupon(expectedCoupon);

        assertNotNull(actualCoupon);
        assertEquals(expectedCoupon.getUuid(), actualCoupon.getUuid());
        assertEquals(expectedCoupon.getName(), actualCoupon.getName());
        assertEquals(expectedCoupon.getDescription(), actualCoupon.getDescription());
        assertEquals(expectedCoupon.getCode(), actualCoupon.getCode());
        assertEquals(expectedCoupon.getCouponType(), actualCoupon.getCouponType());
        assertEquals(expectedCoupon.getDiscount(), actualCoupon.getDiscount());
        assertEquals(expectedCoupon.getAvailableUses(), actualCoupon.getAvailableUses());
    }

    @Test
    public void applyCoupon_then_returnCorrect() {
        String code = "3";
        Coupon coupon = Coupon.builder().name("Test").description("Description").code(code).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        Coupon expectedCoupon = couponService.createCoupon(coupon);

        Coupon actualCoupon = couponService.applyCoupon(code);

        assertNotNull(actualCoupon);
        assertEquals(expectedCoupon.getUuid(), actualCoupon.getUuid());
        assertEquals(expectedCoupon.getName(), actualCoupon.getName());
        assertEquals(expectedCoupon.getCode(), actualCoupon.getCode());
        assertEquals(expectedCoupon.getAvailableUses() - 1, actualCoupon.getAvailableUses());
    }

    @Test
    public void findCouponById_then_returnCorrect() {
        Coupon coupon = Coupon.builder().name("Test").description("Description").code(String.valueOf(System.currentTimeMillis())).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        Coupon expectedCoupon = couponService.createCoupon(coupon);

        Coupon actualCoupon = couponService.findCouponById(expectedCoupon.getUuid());

        assertNotNull(actualCoupon);
        assertEquals(expectedCoupon.getUuid(), actualCoupon.getUuid());
        assertEquals(expectedCoupon.getName(), actualCoupon.getName());
        assertEquals(expectedCoupon.getDescription(), actualCoupon.getDescription());
        assertEquals(expectedCoupon.getCode(), actualCoupon.getCode());
        assertEquals(expectedCoupon.getCouponType(), actualCoupon.getCouponType());
        assertEquals(expectedCoupon.getDiscount(), actualCoupon.getDiscount());
        assertEquals(expectedCoupon.getAvailableUses(), actualCoupon.getAvailableUses());
    }

    @Test
    public void findCouponByCode_then_returnCorrect() {
        String code = String.valueOf(System.currentTimeMillis());
        Coupon coupon = Coupon.builder().name("Test").description("Description").code(code).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        Coupon expectedCoupon = couponService.createCoupon(coupon);

        Coupon actualCoupon = couponService.findCouponByCode(code);

        assertNotNull(actualCoupon);
        assertEquals(expectedCoupon.getUuid(), actualCoupon.getUuid());
        assertEquals(expectedCoupon.getName(), actualCoupon.getName());
        assertEquals(expectedCoupon.getDescription(), actualCoupon.getDescription());
        assertEquals(expectedCoupon.getCode(), actualCoupon.getCode());
        assertEquals(expectedCoupon.getCouponType(), actualCoupon.getCouponType());
        assertEquals(expectedCoupon.getDiscount(), actualCoupon.getDiscount());
        assertEquals(expectedCoupon.getAvailableUses(), actualCoupon.getAvailableUses());
    }

    @Test
    public void findAllCoupons_then_returnCorrect() {
        Coupon coupon = Coupon.builder().name("Test").description("Description").code(String.valueOf(System.currentTimeMillis())).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        Coupon expectedCoupon = couponService.createCoupon(coupon);

        List<Coupon> actualList = couponService.findAllCoupons();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
        assertTrue(actualList.contains(expectedCoupon));
    }

    @Test
    public void deleteCoupon_then_returnCorrect() {
        Coupon coupon = Coupon.builder().name("Test").description("Description").code(String.valueOf(System.currentTimeMillis())).couponType(CouponTypeEnum.FIXED)
                .discount(5).availableUses(5).build();
        Coupon createdCoupon = couponService.createCoupon(coupon);

        couponService.deleteCoupon(createdCoupon.getUuid());

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            couponService.findCouponById(createdCoupon.getUuid());
        });
    }
}
