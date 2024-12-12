package by.ita.chernook.service;

import by.ita.chernook.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainService {

    private final CartService cartService;

    public UUID getCartUuidFromSession(HttpSession session) {
        UUID cartUuid = (UUID) session.getAttribute("cart");
        if (cartUuid == null) {
            Cart cart = cartService.createCart(new Cart());
            session.setAttribute("cart", cart.getUuid());
            return cart.getUuid();
        }
        return cartUuid;
    }
}
