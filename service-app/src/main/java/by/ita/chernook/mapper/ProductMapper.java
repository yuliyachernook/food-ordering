package by.ita.chernook.mapper;

import by.ita.chernook.dto.to_data_base.ProductDatabaseDto;
import by.ita.chernook.dto.to_web.ProductWebDto;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class ProductMapper {

    public ProductDatabaseDto toDatabaseDTO(Product product) {
        return ProductDatabaseDto.builder()
                .uuid(product.getUuid())
                .itemName(product.getItemName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .discountPercentage(product.getDiscountPercentage())
                .image(product.getImage())
                .build();
    }

    public ProductWebDto toWebDTO(Product product) {
        return ProductWebDto.builder()
                .uuid(product.getUuid())
                .itemName(product.getItemName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .discountPercentage(product.getDiscountPercentage())
                .image(product.getImage())
                .build();
    }

    public Product toEntity(ProductDatabaseDto productDatabaseDto) {
        return Product.builder()
                .uuid(productDatabaseDto.getUuid())
                .itemName(productDatabaseDto.getItemName())
                .price(productDatabaseDto.getPrice())
                .description(productDatabaseDto.getDescription())
                .category(productDatabaseDto.getCategory())
                .discountPercentage(productDatabaseDto.getDiscountPercentage())
                .image(productDatabaseDto.getImage())
                .creationDateTime(ZonedDateTime.now())
                .build();
    }

    public Product toEntity(ProductWebDto productWebDto) {
        return Product.builder()
                .uuid(productWebDto.getUuid())
                .itemName(productWebDto.getItemName())
                .price(productWebDto.getPrice())
                .description(productWebDto.getDescription())
                .category(productWebDto.getCategory())
                .discountPercentage(productWebDto.getDiscountPercentage())
                .image(productWebDto.getImage())
                .creationDateTime(ZonedDateTime.now())
                .build();
    }

}
