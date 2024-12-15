package by.ita.chernook.service;

import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String REQUEST_READ_ALL = "/product/read/all";
    private static final String REQUEST_READ_ALL_BY_CATEGORY = "/product/read/all/category";
    private static final String REQUEST_READ = "/product/read?uuid={uuid}";
    private static final String REQUEST_CREATE = "/product/create";
    private static final String REQUEST_UPDATE = "/product/update?uuid={uuid}";
    private static final String REQUEST_DELETE = "/product/delete?uuid={uuid}";

    private final RestTemplate restTemplate;
    private final ProductMapper productMapper;

    public List<Product> readAllProductsForPage(CategoryEnum category) {
        return (category != null) ? readAllProductsByCategory(category): readAllProducts();
    }

    public List<Product> readAllProducts() {
        ResponseEntity<List<ProductWebDto>> response = restTemplate.exchange(REQUEST_READ_ALL, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductWebDto>>(){});
        return response.getBody().stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());
    }

    public List<Product> readAllProductsByCategory(CategoryEnum category) {
        ResponseEntity<List<ProductWebDto>> response = restTemplate.exchange(REQUEST_READ_ALL_BY_CATEGORY+"?category="+ category, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductWebDto>>(){});
        return response.getBody().stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());
    }

    public Product readProductById(String uuid) {
        return productMapper.toEntity(restTemplate.getForObject(REQUEST_READ, ProductWebDto.class, uuid));
    }

    public Product createProduct(Product product, MultipartFile image) {
        try {
            product.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, productMapper.toWebDTO(product), ProductWebDto.class));
    }

    public Product updateProduct(Product product, MultipartFile image) {
        if (image.getSize() > 0) {
            try {
                product.setImage(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        restTemplate.put(REQUEST_UPDATE, productMapper.toWebDTO(product), ProductWebDto.class);
        return product;
    }

    public void deleteProductById(String uuid) {
        restTemplate.delete(REQUEST_DELETE, uuid);
    }
}
