package by.ita.chernook.mapper;

import by.ita.chernook.dto.ProductDto;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class ProductMapper {

    public ProductDto toDTO(Product product) {
        return ProductDto.builder()
                .uuid(product.getUuid())
                .itemName(product.getItemName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .discountPercentage(product.getDiscountPercentage())
                .build();
    }

    public Product toEntity(ProductDto productDto) {
        return Product.builder()
                .uuid(productDto.getUuid())
                .itemName(productDto.getItemName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .discountPercentage(productDto.getDiscountPercentage())
                .creationDateTime(ZonedDateTime.now())
                .build();
    }

}
