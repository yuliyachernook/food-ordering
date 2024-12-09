package by.ita.chernook.service;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.model.Product;
import by.ita.chernook.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findProductById(UUID uuid) {
        return productRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Product with UUID: %s not found", uuid)));

    }

    public List<Product> findProductsByCategory(CategoryEnum category) {
        return productRepository.findByCategory(category);
    }

    public Product insertProduct(Product product) {
        product.setCreationDateTime(ZonedDateTime.now());

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getUuid())) {
            throw new NoSuchElementException(String.format("Product with UUID: %s not found", product.getUuid()));
        }
        return productRepository.save(product);
    }

    public Product deleteProduct(UUID uuid) {
        Product product = findProductById(uuid);
        productRepository.deleteById(uuid);
        return product;
    }

    public List<Product> deleteAllProducts() {
        productRepository.deleteAll();
        return productRepository.findAll();
    }
}
