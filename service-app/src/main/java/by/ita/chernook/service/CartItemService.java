package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CartItemDatabaseDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private static final String REQUEST_CREATE = "/cart/item/create";
    private static final String REQUEST_UPDATE = "/cart/item/update";
    private static final String REQUEST_READ = "/cart/item/read/?uuid=%s";
    private static final String REQUEST_READ_ALL_BY_CART_UUID = "/cart/item/read/all/cart/%s";
    private static final String REQUEST_READ_BY_CART_UUID_AND_PRODUCT_UUID = "/cart/item/read/cart/%s/product/%s";
    private static final String REQUEST_DELETE = "/cart/item/delete?uuid=%s";
    private static final String REQUEST_DELETE_ALL_BY_CART_UUID = "/cart/item/delete/all/cart/%s";

    private final RestTemplate restTemplate;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartItemMapper cartItemMapper;
    private final CartService cartService;

    public CartItem createCartItem(UUID cartUuid, UUID productUuid, int quantity) {
        Cart cart = cartService.findCartById(cartUuid);
        Product product = productService.findProductById(productUuid);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        return cartItemMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, cartItemMapper.toDatabaseDTO(cartItem), CartItemDatabaseDto.class));
    }

    public CartItem updateCartItem(UUID cartUuid, UUID productUuid, int quantity) {
        String url = String.format(REQUEST_READ_BY_CART_UUID_AND_PRODUCT_UUID, cartUuid, productUuid);
        CartItem cartItem = cartItemMapper.toEntity(restTemplate.getForObject(url, CartItemDatabaseDto.class));
        cartItem.setQuantity(quantity);

        restTemplate.put(REQUEST_UPDATE, cartItemMapper.toDatabaseDTO(cartItem), CartItemDatabaseDto.class);
        return cartItemMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, cartItem.getUuid()), CartItemDatabaseDto.class));
    }

    public List<CartItem> findAllCartItemsByCustomerUuid(UUID customerUuid) {
        Customer customer = customerService.findCustomerById(customerUuid);
        UUID cartUuid = customer.getCart().getUuid();

        return findAllCartItemsByCartUuid(cartUuid);
    }

    public List<CartItem> findAllCartItemsByCartUuid(UUID cartUuid) {
        ResponseEntity<List<CartItemDatabaseDto>> response = restTemplate.exchange(String.format(REQUEST_READ_ALL_BY_CART_UUID, cartUuid),
                HttpMethod.GET, null, new ParameterizedTypeReference<>(){});

        return response.getBody().stream()
                .map(cartItemMapper::toEntity)
                .collect(Collectors.toList());
    }

    public void deleteCartItem(UUID cartItemUuid) {
        restTemplate.delete(String.format(REQUEST_DELETE, cartItemUuid));
    }

    public void deleteAllCartItemsByCartUuid(UUID cartUuid) {
        restTemplate.delete(String.format(REQUEST_DELETE_ALL_BY_CART_UUID, cartUuid));
    }

    public void transferCartItemsFromTempToUserCart(UUID cartUuid, UUID tempCartUuid) {
        List<CartItem> userCart = findAllCartItemsByCartUuid(cartUuid);
        List<CartItem> tempCart = findAllCartItemsByCartUuid(tempCartUuid);

        for (CartItem item : tempCart) {
            Optional<CartItem> existingItem = userCart.stream()
                    .filter(cartItem -> cartItem.getProduct().getUuid().equals(item.getProduct().getUuid()))
                    .findFirst();

            if (existingItem.isPresent()) {
                updateCartItem(cartUuid, item.getProduct().getUuid(),existingItem.get().getQuantity() + item.getQuantity());
            } else {
                createCartItem(cartUuid, item.getProduct().getUuid(), item.getQuantity());
            }
        }
    }
}
