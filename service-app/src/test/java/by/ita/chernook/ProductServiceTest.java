package by.ita.chernook;

import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.ProductService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends TestUtils {

    @Mock
    private ProductMapper productMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_then_return_insertedProduct() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = Product.builder().uuid(productUuid).itemName("Item").price(BigDecimal.valueOf(55.55)).build();
        ProductDatabaseDto testProductDatabaseDto = ProductDatabaseDto.builder().uuid(productUuid).itemName("Item").price(BigDecimal.valueOf(55.55)).build();

        when(productMapper.toDatabaseDTO(testProduct)).thenReturn(testProductDatabaseDto);
        when(productMapper.toEntity(restTemplate.postForObject(REQUEST_PRODUCT_CREATE, testProductDatabaseDto, ProductDatabaseDto.class))).thenReturn(testProduct);

        Product actualProduct = productService.createProduct(testProduct);

        assertEquals(testProduct, actualProduct);

        verify(productMapper, times(1)).toDatabaseDTO(testProduct);
        verify(productMapper, times(1)).toEntity(restTemplate.postForObject(REQUEST_PRODUCT_CREATE, testProductDatabaseDto, ProductDatabaseDto.class));
    }

    @Test
    void updateProduct_then_return_updatedProduct() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = Product.builder().uuid(productUuid).itemName("Item").price(BigDecimal.valueOf(55.55)).build();
        ProductDatabaseDto testProductDatabaseDto = ProductDatabaseDto.builder().uuid(productUuid).itemName("Item").price(BigDecimal.valueOf(55.55)).build();

        when(productMapper.toDatabaseDTO(testProduct)).thenReturn(testProductDatabaseDto);
        when(productMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_PRODUCT_READ, productUuid), ProductDatabaseDto.class))).thenReturn(testProduct);

        Product actualProduct = productService.updateProduct(testProduct);

        assertEquals(testProduct, actualProduct);

        verify(productMapper, times(1)).toDatabaseDTO(testProduct);
        verify(productMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_PRODUCT_READ, productUuid), ProductDatabaseDto.class));
    }

    @Test
    void findProductById_then_return_existingProduct() {
        UUID productUuid = UUID.randomUUID();
        Product testProduct = Product.builder().uuid(productUuid).itemName("Item").price(BigDecimal.valueOf(55.55)).build();

        when(productMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_PRODUCT_READ, productUuid), ProductDatabaseDto.class))).thenReturn(testProduct);

        Product actualProduct = productService.findProductById(productUuid);

        assertEquals(testProduct, actualProduct);

        verify(productMapper, times(1)).toEntity(restTemplate.getForObject(String.format(REQUEST_PRODUCT_READ, productUuid), ProductDatabaseDto.class));
    }

    @Test
    void deleteProduct() {
        UUID uuid = UUID.randomUUID();
        productService.deleteProduct(uuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_PRODUCT_DELETE, uuid));
    }
}
