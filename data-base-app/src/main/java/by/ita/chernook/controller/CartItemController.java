package by.ita.chernook.controller;

import by.ita.chernook.dto.CartItemDto;
import by.ita.chernook.mapper.CartItemMapper;
import by.ita.chernook.model.CartItem;
import by.ita.chernook.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/cart/item")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    @PostMapping("/create")
    public CartItemDto create(@RequestBody CartItemDto cartItemDto) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDto);
        CartItem insertedCartItem = cartItemService.insertCartItem(cartItem);
        return cartItemMapper.toDTO(insertedCartItem);
    }

    @PutMapping("/update")
    public CartItemDto update(@RequestBody CartItemDto cartItemDto) {
        CartItem updatedCartItem = cartItemService.updateCartItem(cartItemMapper.toEntity(cartItemDto));
        return cartItemMapper.toDTO(updatedCartItem);
    }

    @DeleteMapping("/delete")
    public CartItemDto delete(@RequestParam UUID id) {
        CartItem deletedCartItem = cartItemService.deleteCartItem(id);
        return cartItemMapper.toDTO(deletedCartItem);
    }

    @DeleteMapping("/delete/all")
    public List<CartItemDto> deleteAll() {
        return cartItemService.deleteAllCartItems()
                .stream()
                .map(cartItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/cart/product")
    public CartItemDto readByCartUuidAndProductUuid(@RequestParam UUID cartUuid, @RequestParam UUID productUuid) {
        return cartItemMapper.toDTO(cartItemService.findAllByCartUuidAndProductUuid(cartUuid, productUuid));
    }

    @GetMapping("/read/all/cart")
    public List<CartItemDto> readAllByCartUuid(@RequestParam UUID cartUuid) {
        return cartItemService.findAllByCartUuid(cartUuid)
                .stream()
                .map(cartItemMapper::toDTO)
                .collect(Collectors.toList());
    }
}
