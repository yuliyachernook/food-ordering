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
    public CartDto create(@RequestBody CartDto productDto) {
        Cart cart = cartMapper.toEntity(productDto);
        Cart insertedCart = cartService.insertCart(cart);
        return cartMapper.toDTO(insertedCart);
    }

    @GetMapping("/read")
    public CartDto read(@RequestParam UUID id) {
        Cart cart = cartService.findCartById(id);
        return cartMapper.toDTO(cart);
    }

    @GetMapping("/read/all")
    public List<CartDto> readAll() {
        return cartService.findAll()
                .stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    public CartDto update(@RequestParam UUID id, @RequestBody CartDto cartDto) {
        Cart updatedCart = cartService.updateCart(cartMapper.toEntity(cartDto));
        return cartMapper.toDTO(updatedCart);
    }

    @DeleteMapping("/delete")
    public CartDto delete(@RequestParam UUID id) {
        Cart deletedCart = cartService.deleteCart(id);
        return cartMapper.toDTO(deletedCart);
    }
}
