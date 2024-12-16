package by.ita.chernook;

import by.ita.chernook.model.*;
import by.ita.chernook.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CartServiceIT {

    @Autowired
    private CartService cartService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(cartService);
    }

    @Test
    public void createCart_then_returnCorrect() {
        Cart expectedCart = cartService.createCart(Cart.builder().build());

        Cart actualCart = cartService.createCart(expectedCart);

        assertNotNull(actualCart);
    }

    @Test
    public void findCartById_then_returnCorrect() {
        Cart expectedCart = cartService.createCart(Cart.builder().build());

        Cart actualCart = cartService.findCartById(expectedCart.getUuid());

        assertNotNull(actualCart);
        assertEquals(expectedCart, actualCart);
    }

    @Test
    public void deleteCart_then_returnCorrect() {
        Cart cart = cartService.createCart(Cart.builder().build());

        cartService.deleteCart(cart.getUuid());

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            cartService.findCartById(cart.getUuid());
        });
    }
}
