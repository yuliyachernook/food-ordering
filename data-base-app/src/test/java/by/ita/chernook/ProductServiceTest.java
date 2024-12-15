package by.ita.chernook;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.model.Product;
import by.ita.chernook.repository.ProductRepository;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest extends TestUtils {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void insertProduct_then_return() {
        Product testProduct = buildProduct(UUID.randomUUID(), "Test item", BigDecimal.valueOf(25.2), "Test description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product actualProduct = productService.insertProduct(testProduct);

        assertEquals(testProduct, actualProduct);

        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void updateProduct_then_return() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = buildProduct(productUuid, "Test item", BigDecimal.valueOf(10.15), "Updated description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(productRepository.existsById(productUuid)).thenReturn(true);
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product actualProduct = productService.updateProduct(testProduct);

        assertEquals(testProduct, actualProduct);

        verify(productRepository, times(1)).existsById(productUuid);
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void updateProduct_then_throws_NoSuchElementException() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = buildProduct(productUuid, "Test item", BigDecimal.valueOf(25.2), "Test description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(productRepository.existsById(productUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> productService.updateProduct(testProduct));

        verify(productRepository, times(1)).existsById(productUuid);
        verify(productRepository, times(0)).save(testProduct);
    }

    @Test
    void deleteProduct_then_return() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = buildProduct(productUuid, "Test item", BigDecimal.valueOf(25.2), "Test description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(productRepository.findById(productUuid)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).deleteById(productUuid);

        Product actualProduct = productService.deleteProduct(productUuid);

        assertEquals(testProduct, actualProduct);

        verify(productRepository, times(1)).findById(productUuid);
        verify(productRepository, times(1)).deleteById(productUuid);
    }

    @Test
    void deleteProduct_then_throws_NoSuchElementException() {
        UUID productUuid = UUID.randomUUID();

        when(productRepository.findById(productUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> productService.deleteProduct(productUuid));

        verify(productRepository, times(1)).findById(productUuid);
        verify(productRepository, times(0)).deleteById(productUuid);
    }

    @Test
    void findProductById_then_return() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = buildProduct(productUuid, "Test item", BigDecimal.valueOf(25.2), "Test description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));

        when(productRepository.findById(productUuid)).thenReturn(Optional.of(testProduct));

        Product actualProduct = productService.findProductById(productUuid);

        assertEquals(testProduct, actualProduct);

        verify(productRepository, times(1)).findById(productUuid);
    }

    @Test
    void findProductById_then_throws_NoSuchElementException() {
        UUID productUuid = UUID.randomUUID();

        when(productRepository.findById(productUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> productService.findProductById(productUuid));

        verify(productRepository, times(1)).findById(productUuid);
    }

    @Test
    void findAll_then_return() {
        Product testProduct = buildProduct(UUID.randomUUID(), "Test item", BigDecimal.valueOf(25.2), "Test description", CategoryEnum.PIZZA,
                10, ZonedDateTime.parse("2024-09-09T00:28:39.294+03:00"));
        Product testProduct2 = buildProduct(UUID.randomUUID(), "Test item 2", BigDecimal.valueOf(48.4), "Test description 2", CategoryEnum.DRINKS,
                5, ZonedDateTime.parse("2024-09-09T00:28:39.295+03:00"));
        List<Product> testList = new ArrayList<>(Arrays.asList(testProduct, testProduct2));

        when(productRepository.findAll()).thenReturn(testList);

        List<Product> actualList = productService.findAll();

        assertIterableEquals(testList, actualList);

        verify(productRepository, times(1)).findAll();
    }


    @Test
    void deleteAll_then_return_emptyList() {
        doNothing().when(productRepository).deleteAll();
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> actualList = productService.deleteAllProducts();

        assertIterableEquals(Collections.emptyList(), actualList);

        verify(productRepository, times(1)).deleteAll();
        verify(productRepository, times(1)).findAll();
    }
}