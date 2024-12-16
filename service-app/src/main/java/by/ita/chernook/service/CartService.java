package by.ita.chernook.service;

import by.ita.chernook.dto.to_data_base.CartDatabaseDto;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String REQUEST_CREATE = "/cart/create";
    private static final String REQUEST_READ = "/cart/read?uuid=%s";
    private static final String REQUEST_DELETE = "/cart/delete?uuid=%s";

    private final RestTemplate restTemplate;
    private final CartMapper cartMapper;

    public Cart createCart(Cart cart) {
        CartDatabaseDto cartDatabaseDto = cartMapper.toDatabaseDTO(cart);
        return cartMapper.toEntity(restTemplate.postForObject(REQUEST_CREATE, cartDatabaseDto, CartDatabaseDto.class));
    }

    public Cart findCartById(UUID cartUuid) {
        return cartMapper.toEntity(restTemplate.getForObject(String.format(REQUEST_READ, cartUuid), CartDatabaseDto.class));
    }

    public void deleteCart(UUID cartUuid) {
        restTemplate.delete(String.format(REQUEST_DELETE, cartUuid));
    }
}
