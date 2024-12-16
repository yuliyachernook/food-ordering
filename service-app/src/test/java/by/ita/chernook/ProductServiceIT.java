package by.ita.chernook;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.model.*;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceIT extends TestUtils {

    @Autowired
    private ProductService productService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("database.api.base-url", () -> "http://localhost:8201/database");
    }

    @Test
    public void contextTest(){
        assertNotNull(productService);
    }

    @Test
    public void createProduct_then_returnCorrect() {
        Product expectedProduct = buildProduct(UUID.randomUUID(), "Item", BigDecimal.valueOf(5.55), "Description", CategoryEnum.PIZZA,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        Product actualProduct = productService.createProduct(expectedProduct);

        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getItemName(), actualProduct.getItemName());
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getCategory(), actualProduct.getCategory());
        assertEquals(expectedProduct.getDiscountPercentage(), actualProduct.getDiscountPercentage());
    }

    @Test
    public void updateProduct_then_returnCorrect() {
        Product product = buildProduct(UUID.randomUUID(), "Item", BigDecimal.valueOf(5.55), "Description", CategoryEnum.PIZZA,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Product createdProduct = productService.createProduct(product);
        Product expectedProduct = buildProduct(createdProduct.getUuid(), "Item", BigDecimal.valueOf(10.55), "Description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        Product actualProduct= productService.updateProduct(expectedProduct);

        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getItemName(), actualProduct.getItemName());
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getCategory(), actualProduct.getCategory());
        assertEquals(expectedProduct.getDiscountPercentage(), actualProduct.getDiscountPercentage());
    }

    @Test
    public void findProductById_then_returnCorrect() {
        Product product = buildProduct(UUID.randomUUID(), "Item", BigDecimal.valueOf(5.55), "Description", CategoryEnum.PIZZA,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Product expectedProduct = productService.createProduct(product);

        Product actualProduct = productService.findProductById(expectedProduct.getUuid());

        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getItemName(), actualProduct.getItemName());
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getCategory(), actualProduct.getCategory());
        assertEquals(expectedProduct.getDiscountPercentage(), actualProduct.getDiscountPercentage());
    }

    @Test
    public void findAll_then_returnCorrect() {
        Product product = buildProduct(UUID.randomUUID(), "Item", BigDecimal.valueOf(5.55), "Description", CategoryEnum.PIZZA,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        productService.createProduct(product);

        List<Product> actualList = productService.findAll();

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
    }

    @Test
    public void findAllByCategory_then_returnCorrect() {
        Product product = buildProduct(UUID.randomUUID(), "Item", BigDecimal.valueOf(5.55), "Description", CategoryEnum.PIZZA,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        productService.createProduct(product);

        List<Product> actualList = productService.findAllByCategory(CategoryEnum.PIZZA);

        assertNotNull(actualList);
        assertFalse(actualList.isEmpty());
    }

    @Test
    public void deleteProduct_then_returnCorrect() {
        Product product = buildProduct(UUID.randomUUID(), "Item", BigDecimal.valueOf(5.55), "Description", CategoryEnum.PIZZA,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Product createdProduct = productService.createProduct(product);

        productService.deleteProduct(createdProduct.getUuid());

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            productService.findProductById(createdProduct.getUuid());
        });
    }
}
