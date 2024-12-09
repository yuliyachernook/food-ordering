package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.ProductWebDto;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/delivery/address/product")
public class DeliveryAddressController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ProductWebDto create(@RequestBody ProductWebDto productDatabaseDto) {
        Product product = productMapper.toEntity(productDatabaseDto);
        Product insertedProduct = productService.createProduct(product);
        return productMapper.toWebDTO(insertedProduct);
    }
}
