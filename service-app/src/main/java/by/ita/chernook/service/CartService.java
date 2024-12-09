package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String REQUEST_CART_CREATE = "/cart/create";
    private static final String REQUEST_READ = "/cart/read?uuid=%s";
    private static final String REQUEST_DELETE = "/cart/delete?uuid=%s";
    private static final String REQUEST_CLEAN = "/cart/clean?uuid=%s";

    private final RestTemplate restTemplate;
    private final CustomerService customerService;
    private final CartMapper cartMapper;

    public Cart createCart(Cart cart) {
        CartDatabaseDto cartDatabaseDto = cartMapper.toDatabaseDTO(cart);
        return cartMapper.toEntity(restTemplate.postForObject(REQUEST_CART_CREATE, cartDatabaseDto, CartDatabaseDto.class));
    }

    public void deleteCart(UUID cartUuid) {
        restTemplate.delete(String.format(REQUEST_DELETE, cartUuid));
    }

    public void cleanCart(UUID customerUuid) {
        Customer customer = customerService.findCustomerById(customerUuid);
        restTemplate.delete(String.format(REQUEST_CLEAN, customer.getCart().getUuid()));
    }

    public Cart findCartById(UUID cartUuid) {
        String url = String.format(REQUEST_READ, cartUuid);
        return cartMapper.toEntity(restTemplate.getForObject(url, CartDatabaseDto.class));
    }
}
