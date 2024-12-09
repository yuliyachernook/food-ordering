package by.ita.chernook.controller;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.dto.to_web.ProductWebDto;
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
@RequestMapping("/business/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ProductWebDto create(@RequestBody ProductWebDto productDatabaseDto) {
        Product product = productMapper.toEntity(productDatabaseDto);
        Product insertedProduct = productService.createProduct(product);
        return productMapper.toWebDTO(insertedProduct);
    }


    @PutMapping("/update")
    public ProductWebDto update(@RequestBody ProductWebDto productDatabaseDto) {
        Product updatedProduct = productService.updateProduct(productMapper.toEntity(productDatabaseDto));
        return productMapper.toWebDTO(updatedProduct);
    }

    @GetMapping("/read")
    public ProductWebDto read(@RequestParam UUID id) {
        Product product = productService.findProductById(id);
        return productMapper.toWebDTO(product);
    }

    @GetMapping("/read/all")
    public List<ProductWebDto> readAll() {
        return productService.findAll()
                .stream()
                .map(productMapper::toWebDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/all/category")
    public List<ProductWebDto> readAllByCategory(@RequestParam CategoryEnum category) {
        return productService.findAllByCategory(category)
                .stream()
                .map(productMapper::toWebDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam UUID id) {
        productService.deleteProduct(id);
    }
//
//    @DeleteMapping("/delete/all")
//    public List<ProductWebDto> deleteAll() {
//        return productService.deleteAllProducts()
//                .stream()
//                .map(productMapper::toDatabaseDTO)
//                .collect(Collectors.toList());
//    }
}
