package by.ita.chernook.service;

import by.ita.chernook.model.CartItem;
import by.ita.chernook.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItem insertCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        if (!cartItemRepository.existsById(cartItem.getUuid())) {
            throw new NoSuchElementException(String.format("CartItem with UUID: %s not found", cartItem.getUuid()));
        }
        return cartItemRepository.save(cartItem);
    }

    public CartItem deleteCartItem(UUID uuid) {
        CartItem cartItem = findCartItemById(uuid);
        cartItemRepository.deleteById(uuid);
        return cartItem;
    }

    @Transactional
    public void deleteAllCartItemsByCartUuid(UUID cartUuid) {
        cartItemRepository.deleteByCartUuid(cartUuid);
    }

    public CartItem findCartItemById(UUID uuid) {
        return cartItemRepository.findById(uuid).orElseThrow(() ->
                new NoSuchElementException(String.format("CartItem with UUID: %s not found", uuid)));
    }

    public CartItem findByCartUuidAndProductUuid(UUID cartUuid, UUID productUuid) {
        return cartItemRepository.findByCartUuidAndProductUuid(cartUuid, productUuid).orElseThrow(() ->
                new NoSuchElementException(String.format("CartItem with cart UUID: %s and product UUID: %s not found", cartUuid, productUuid)));
    }

    public List<CartItem> findAllByCartUuid(UUID cartUuid) {
        return cartItemRepository.findByCartUuid(cartUuid);
    }
}
