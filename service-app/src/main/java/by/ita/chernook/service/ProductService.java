package by.ita.chernook.service;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String REQUEST_READ_ALL = "/product/read/all";
    private static final String REQUEST_READ = "/product/read?";
    private static final String REQUEST_READ_ALL_BY_CATEGORY = "/product/read/all/category/";
    private static final String REQUEST_CREATE = "/product/create";
    private static final String REQUEST_UPDATE = "/product/update";
    private static final String REQUEST_DELETE = "/product/delete/";

    private final RestTemplate restTemplate;
    private final ProductMapper productMapper;

    public List<Product> findAll() {
        ResponseEntity<List<ProductDatabaseDto>> response = restTemplate.exchange(REQUEST_READ_ALL, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());
    }

    public List<Product> findAllByCategory(CategoryEnum category) {
        ResponseEntity<List<ProductDatabaseDto>> response = restTemplate.exchange(REQUEST_READ_ALL_BY_CATEGORY + category, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody().stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());
    }

    public Product findProductById(UUID uuid) {
        return productMapper.toEntity(restTemplate.getForObject(REQUEST_READ + "id=" + uuid, ProductDatabaseDto.class));
    }


    public Product createProduct(Product product) {
        ProductDatabaseDto productDatabaseDto = productMapper.toDatabaseDTO(product);
        return productMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, productDatabaseDto, ProductDatabaseDto.class));
    }

    public Product updateProduct(Product product) {
        ProductDatabaseDto productDatabaseDto = productMapper.toDatabaseDTO(product);
        restTemplate.put(REQUEST_UPDATE, productDatabaseDto);
        return findProductById(product.getUuid());
    }

    public void deleteProduct(UUID uuid) {
        restTemplate.delete(REQUEST_DELETE  + uuid);
    }
}
