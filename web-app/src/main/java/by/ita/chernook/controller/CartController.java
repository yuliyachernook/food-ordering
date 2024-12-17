package by.ita.chernook.controller;

import by.ita.chernook.model.CartItem;
import by.ita.chernook.model.CustomUserDetails;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final MainService mainService;
    private final CartService cartService;

    @GetMapping
    public String showCartPage(HttpSession session, Model model) {
        List<CartItem> cartItems = cartService.readAllCartItemsByCartUuid(mainService.getCartUuidFromSession(session));
        model.addAttribute("cartItems", cartItems);
        session.removeAttribute("discountedTotalPrice");
        session.removeAttribute("coupon");
        return "cart";
    }

    @PostMapping("/add-cart-item")
    public String addToCart(HttpSession session, @RequestParam String productId, @RequestParam String quantity) {
       cartService.addCartItem(mainService.getCartUuidFromSession(session), productId, quantity);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCartItem(HttpSession session, @RequestParam String productId, @RequestParam String quantity, @RequestParam(required = false) String redirectPage) {
        cartService.updateCartItemQuantity(mainService.getCartUuidFromSession(session), productId, quantity);
        if (redirectPage != null) {
            return "redirect:/";
        }
        return "redirect:/cart";
    }

    @PostMapping("/delete")
    public String deleteFromCart(Model model, @RequestParam String cartItemId) {
        cartService.deleteCartItemById(cartItemId);
        return "redirect:/";
    }

    @PostMapping("/clean")
    public String cleanCart(HttpSession session, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        cartService.cleanCart(mainService.getCartUuidFromSession(session));
        return "redirect:/";
    }
}