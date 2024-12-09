package by.ita.chernook.service;

import by.ita.chernook.model.Cart;
import by.ita.chernook.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart insertCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart) {
        if (!cartRepository.existsById(cart.getUuid())) {
            throw new NoSuchElementException(String.format("Cart with UUID: %s not found", cart.getUuid()));
        }
        if (cart.getCartItems() != null) {
            cart.getCartItems().forEach(cartItem -> cartItem.setCart(cart));
        }
        return cartRepository.save(cart);
    }

    public Cart deleteCart(UUID uuid) {
        Cart cart = findCartById(uuid);
        cartRepository.deleteById(uuid);
        return cart;
    }

    public void cleanCart(UUID uuid) {
        Cart cart = findCartById(uuid);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    public Cart findCartById(UUID uuid) {
        return cartRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("Cart with UUID: %s not found", uuid)));

    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
}
