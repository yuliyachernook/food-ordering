package by.ita.chernook.controller;

import by.ita.chernook.dto.ProductDto;
import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ProductDto create(@RequestBody ProductDto productDto) {
        Product insertedProduct = productService.insertProduct(productMapper.toEntity(productDto));
        return productMapper.toDTO(insertedProduct);
    }

    @PutMapping("/update")
    public ProductDto update(@RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProduct(productMapper.toEntity(productDto));
        return productMapper.toDTO(updatedProduct);
    }

    @GetMapping("/read")
    public ProductDto read(@RequestParam UUID uuid) {
        Product product = productService.findProductById(uuid);
        return productMapper.toDTO(product);
    }

    @GetMapping("/read/all")
    public List<ProductDto> readAll() {
        return productService.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/all/category/{category}")
    public List<ProductDto> readAllByCategory(@PathVariable CategoryEnum category) {
        return productService.findProductsByCategory(category)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public ProductDto delete(@RequestParam UUID uuid) {
        Product deletedProduct = productService.deleteProduct(uuid);
        return productMapper.toDTO(deletedProduct);
    }

    @DeleteMapping("/delete/all")
    public List<ProductDto> deleteAll() {
        return productService.deleteAllProducts()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable UUID id) {
        Product product = productService.findProductById(id);
        if (product != null && product.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
