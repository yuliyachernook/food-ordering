package by.ita.chernook.controller;

import by.ita.chernook.dto.ProductDto;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ProductDto create(@RequestBody ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product insertedProduct = productService.insertProduct(product);
        return productMapper.toDTO(insertedProduct);
    }

    @GetMapping("/read")
    public ProductDto read(@RequestParam UUID id) {
        Product product = productService.findProductById(id);
        return productMapper.toDTO(product);
    }

    @GetMapping("/read/all")
    public List<ProductDto> readAll() {
        return productService.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    public ProductDto update(@RequestParam UUID id, @RequestBody ProductDto cosmeticBrandDto) {
        Product updatedProduct = productService.updateProduct(productMapper.toEntity(cosmeticBrandDto));
        return productMapper.toDTO(updatedProduct);
    }

    @DeleteMapping("/delete")
    public ProductDto delete(@RequestParam UUID id) {
        Product deletedProduct = productService.deleteProduct(id);
        return productMapper.toDTO(deletedProduct);
    }

    @DeleteMapping("/delete/all")
    public List<ProductDto> deleteAll() {
        return productService.deleteAllProducts()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}
