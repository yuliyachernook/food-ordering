package by.ita.chernook.service;

import by.ita.chernook.model.Cart;
import by.ita.chernook.model.Product;
import by.ita.chernook.repository.CartRepository;
import by.ita.chernook.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart findCartById(UUID uuid) {
        return cartRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Cart with UUID: %s not found", uuid)));

    }

    public Cart insertCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart) {
        if (!cartRepository.existsById(cart.getUuid())) {
            throw new NoSuchElementException(String.format("Cart with UUID: %s not found", cart.getUuid()));
        }
        return cartRepository.save(cart);
    }

    public Cart deleteCart(UUID uuid) {
        Cart product = findCartById(uuid);
        cartRepository.deleteById(uuid);
        return product;
    }
}
