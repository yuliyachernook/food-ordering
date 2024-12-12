package by.ita.chernook.service;

import by.ita.chernook.dto.CartItemWebDto;
import by.ita.chernook.dto.CartWebDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.CartItem;
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
public class CartService {

    private static final String REQUEST_CREATE_CARTITEM = "/cart/item/create?";
    private static final String REQUEST_UPDATE_CARTITEM = "/cart/item/update?";
    private static final String REQUEST_READ_ALL_CART_ITEMS_BY_CART_UUID = "/cart/item/read/all/cart/%s";
    private static final String REQUEST_TRANSFER = "/cart/item/transfer?";
    private static final String REQUEST_DELETE_CARTITEM = "/cart/item/delete?uuid=%s";
    private static final String REQUEST_CART_CREATE = "/cart/create";
    private static final String REQUEST_DELETE_ALL_CART_ITEMS_BY_CART_UUID = "/cart/item/delete/all/cart/%s";



    private final RestTemplate restTemplate;
    private final CartItemMapper cartItemMapper;
    private final CartMapper cartMapper;


    public Cart createCart(Cart cart) {
       return cartMapper.toEntity(restTemplate.postForObject(REQUEST_CART_CREATE, cartMapper.toWebDTO(cart), CartWebDto.class));
    }

    public void addCartItem(UUID customerUuid, String productId, String quantity) {
        restTemplate.postForEntity(REQUEST_CREATE_CARTITEM + "cartUuid=" + customerUuid + "&productUuid=" + productId + "&quantity=" +quantity, null, Void.class);
    }

    public CartItem updateCartItemQuantity(UUID customerUuid, String productId, String quantity) {
        CartItemWebDto cartItemWebDto = restTemplate.postForObject(REQUEST_UPDATE_CARTITEM + "cartUuid=" + customerUuid + "&productUuid=" + productId + "&quantity=" + quantity, null,CartItemWebDto.class);
        return cartItemMapper.toEntity(cartItemWebDto);
    }

    public void deleteCartItemById(String uuid) {
        restTemplate.delete(String.format(REQUEST_DELETE_CARTITEM, uuid));
    }

    public void cleanCart(UUID cartUuid) {
        restTemplate.delete(String.format(REQUEST_DELETE_ALL_CART_ITEMS_BY_CART_UUID, cartUuid));
    }

    public void transfer(UUID userCartUuid, UUID tempCartUuid) {
        restTemplate.put(REQUEST_TRANSFER + "userCartUuid=" + userCartUuid + "&tempCartUuid=" + tempCartUuid, null);
    }

    public List<CartItem> readAllCartItemsByCartUuid(UUID cartUuid) {
        ResponseEntity<List<CartItemWebDto>> response = restTemplate.exchange(String.format(REQUEST_READ_ALL_CART_ITEMS_BY_CART_UUID, cartUuid), HttpMethod.GET, null, new ParameterizedTypeReference<List<CartItemWebDto>>(){});
        return response.getBody().stream()
                .map(cartItemMapper::toEntity)
                .collect(Collectors.toList());
    }
}
