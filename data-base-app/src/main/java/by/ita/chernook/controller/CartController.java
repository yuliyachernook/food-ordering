package by.ita.chernook.controller;

import by.ita.chernook.dto.CartDto;
import by.ita.chernook.mapper.CartMapper;
import by.ita.chernook.model.Cart;
import by.ita.chernook.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping("/create")
    public CartDto create(@RequestBody CartDto cartDto) {
        Cart insertedCart = cartService.insertCart(cartMapper.toEntity(cartDto));
        return cartMapper.toDTO(insertedCart);
    }

    @PutMapping("/update")
    public CartDto update(@RequestBody CartDto cartDto) {
        Cart updatedCart = cartService.updateCart(cartMapper.toEntity(cartDto));
        return cartMapper.toDTO(updatedCart);
    }

    @GetMapping("/read")
    public CartDto read(@RequestParam UUID uuid) {
        Cart cart = cartService.findCartById(uuid);
        return cartMapper.toDTO(cart);
    }

    @GetMapping("/read/all")
    public List<CartDto> readAll() {
        return cartService.findAll()
                .stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public CartDto delete(@RequestParam UUID uuid) {
        Cart deletedCart = cartService.deleteCart(uuid);
        return cartMapper.toDTO(deletedCart);
    }
}
