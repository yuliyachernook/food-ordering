package by.ita.chernook.controller;

import by.ita.chernook.dto.CartItemWebDto;
import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.MainService;
import by.ita.chernook.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class MainController {
    private final ProductService productService;
    private final MainService mainService;

    private final ProductMapper productMapper;
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;

    @GetMapping("/")
    public String showMainPage(HttpSession session, @RequestParam(required = false) CategoryEnum category, Model model) {

        List<ProductWebDto> products = productService.readAllProductsForPage(category).stream()
                .map(productMapper::toWebDTO)
                .collect(Collectors.toList());

        List<CartItem> cartItems = cartService.readAllCartItemsByCartUuid(mainService.getCartUuidFromSession(session));

        Map<UUID, Integer> productQuantitiesInCart = cartItems.stream()
                .map(cartItemMapper::toWebDTO)
                .collect(Collectors.toMap(item -> item.getProduct().getUuid(),
                        CartItemWebDto::getQuantity
                ));

        model.addAttribute("products", products);
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("productQuantitiesInCart", productQuantitiesInCart);

        return "index";
    }

    @GetMapping("/product")
    public String showProductPage(@RequestParam String uuid, Model model) {
        Product product = productService.readProductById(uuid);
        model.addAttribute("product", product);
        return "productDetails";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable String id) {
        Product product = productService.readProductById(id);
        if (product != null && product.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getImage());
        } else {
            return ResponseEntity.ok().body(null);
        }
    }
}
