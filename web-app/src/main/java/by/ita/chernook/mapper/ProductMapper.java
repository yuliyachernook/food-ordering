package by.ita.chernook.mapper;

import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class ProductMapper {

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
