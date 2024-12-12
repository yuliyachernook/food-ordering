package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.CartWebDto;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/business/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping("/create")
    public CartWebDto create(@RequestBody CartWebDto cartWebDto) {
        Cart insertedCart = cartService.createCart(cartMapper.toEntity(cartWebDto));
        return cartMapper.toWebDTO(insertedCart);
    }

    @GetMapping("/read")
    public CartWebDto read(@RequestParam UUID uuid) {
        Cart cart = cartService.findCartById(uuid);
        return cartMapper.toWebDTO(cart);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam UUID uuid) {
        cartService.deleteCart(uuid);
    }
}
