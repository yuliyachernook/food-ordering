package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CartItemDatabaseDto;
import by.ita.chernook.dto.to_data_base.CustomerDatabaseDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.mapper.CustomerMapper;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private static final String REQUEST_CART_ITEM_CREATE = "/cart/item/create";
    private static final String REQUEST_CART_ITEM_UPDATE = "/cart/item/update";
    private static final String REQUEST_CART_ITEM_DELETE = "/cart/item/delete?uuid=%s";
    private static final String REQUEST_CART_ITEM_READ_BY_UUID = "/cart/item/read/?uuid=%s";
    private static final String REQUEST_CART_ITEM_READ_ALL_BY_CART_UUID = "/cart/item/read/all/cart/%s";
    private static final String REQUEST_CART_ITEM_READ_BY_CART_UUID_AND_PRODUCT_UUID = "/cart/item/read/cart/%s/product/%s";

    private final RestTemplate restTemplate;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartItemMapper cartItemMapper;

    public CartItem createCartItem(UUID customerUuid, UUID productUuid, int quantity) {
        Customer customer = customerService.findCustomerById(customerUuid);
        Product product = productService.findProductById(productUuid);

        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
        }

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        return cartItemMapper.toEntity(restTemplate.postForObject(REQUEST_CART_ITEM_CREATE, cartItemMapper.toDatabaseDTO(cartItem), CartItemDatabaseDto.class));
    }

    public CartItem updateCartItem(UUID customerUuid, UUID productUuid, int quantity) {
        Customer customer = customerService.findCustomerById(customerUuid);

        String url = String.format(REQUEST_CART_ITEM_READ_BY_CART_UUID_AND_PRODUCT_UUID, customer.getCart().getUuid(), productUuid);
        CartItem cartItem = cartItemMapper.toEntity(restTemplate.getForObject(url, CartItemDatabaseDto.class));
        cartItem.setQuantity(quantity);

        restTemplate.put(REQUEST_CART_ITEM_UPDATE, cartItemMapper.toDatabaseDTO(cartItem), CartItemDatabaseDto.class);
        return cartItemMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_CART_ITEM_READ_BY_UUID, cartItem.getUuid()), CartItemDatabaseDto.class));
    }

    public void deleteCartItem(UUID cartItemUuid) {
        restTemplate.delete(REQUEST_CART_ITEM_DELETE, cartItemUuid);
    }

    public List<CartItem> findAllCartItemsByCustomerUuid(UUID customerUuid) {
        Customer customer = customerService.findCustomerById(customerUuid);
        UUID cartUuid = customer.getCart().getUuid();

        return findAllCartItemsByCartUuid(cartUuid);
    }

    public List<CartItem> findAllCartItemsByCartUuid(UUID cartUuid) {
        ResponseEntity<List<CartItemDatabaseDto>> response = restTemplate.exchange(String.format(REQUEST_CART_ITEM_READ_ALL_BY_CART_UUID, cartUuid),
                HttpMethod.GET, null, new ParameterizedTypeReference<>(){});

        return response.getBody().stream()
                .map(cartItemMapper::toEntity)
                .collect(Collectors.toList());
    }
}
