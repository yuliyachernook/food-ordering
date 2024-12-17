package by.ita.chernook;

import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.model.*;
import by.ita.chernook.service.CartItemService;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CartItemServiceIT {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(cartService);
    }

    @Test
    public void createCartItem_then_returnCorrect() {
        Cart cart = cartService.createCart(Cart.builder().build());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        CartItem expectedCartItem = CartItem.builder().cart(cart).product(product).quantity(1).build();

        CartItem actualCartItem = cartItemService.createCartItem(cart.getUuid(), product.getUuid(), 1);

        assertNotNull(actualCartItem);
        assertEquals(expectedCartItem.getCart().getUuid(), actualCartItem.getCart().getUuid());
        assertEquals(expectedCartItem.getProduct().getUuid(), actualCartItem.getProduct().getUuid());
        assertEquals(expectedCartItem.getQuantity(), actualCartItem.getQuantity());
    }

    @Test
    public void updateCartItem_then_returnCorrect() {
        Cart cart = cartService.createCart(Cart.builder().build());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        CartItem expectedCartItem = cartItemService.createCartItem(cart.getUuid(), product.getUuid(), 1);

        int newQuantity = 8;
        CartItem actualCartItem = cartItemService.updateCartItem(cart.getUuid(), product.getUuid(), newQuantity);

        expectedCartItem.setQuantity(8);

        assertNotNull(actualCartItem);
        assertEquals(expectedCartItem.getCart().getUuid(), actualCartItem.getCart().getUuid());
        assertEquals(expectedCartItem.getProduct().getUuid(), actualCartItem.getProduct().getUuid());
        assertEquals(expectedCartItem.getQuantity(), actualCartItem.getQuantity());
    }

    @Test
    public void findAllCartItemsByCustomerUuid_then_returnCorrect() {
        Customer customer = customerService.createCustomer(Customer.builder().user(User.builder().login("login").password("password").build()).build());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        cartItemService.createCartItem(customer.getCart().getUuid(), product.getUuid(), 1);

        List<CartItem> actualList = cartItemService.findAllCartItemsByCustomerUuid(customer.getUuid());

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
    }

    @Test
    public void findAllCartItemsByCartUuid_then_returnCorrect() {
        Cart cart = cartService.createCart(Cart.builder().build());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        cartItemService.createCartItem(cart.getUuid(), product.getUuid(), 1);

        List<CartItem> actualList = cartItemService.findAllCartItemsByCartUuid(cart.getUuid());

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
    }

    @Test
    public void deleteCartItem_then_returnCorrect() {
        Cart cart = cartService.createCart(Cart.builder().build());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        CartItem cartItem = cartItemService.createCartItem(cart.getUuid(), product.getUuid(), 1);

        cartItemService.deleteCartItem(cartItem.getUuid());

        List<CartItem> cartItems = cartItemService.findAllCartItemsByCartUuid(cart.getUuid());
        assertEquals(0, cartItems.size());
    }

    @Test
    public void deleteAllCartItemsByCartUuid_then_returnCorrect() {
        Cart cart = cartService.createCart(Cart.builder().build());
        Product product = productService.createProduct(Product.builder().itemName("Test").build());
        cartItemService.createCartItem(cart.getUuid(), product.getUuid(), 1);

        cartItemService.deleteAllCartItemsByCartUuid(cart.getUuid());

        List<CartItem> cartItems = cartItemService.findAllCartItemsByCartUuid(cart.getUuid());
        assertEquals(0, cartItems.size());
    }
}
