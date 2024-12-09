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

    @GetMapping("/read")
    public CartItemDto read(@RequestParam UUID uuid) {
        return cartItemMapper.toDTO(cartItemService.findCartItemById(uuid));
    }

    @GetMapping("/read/cart/{cartUuid}/product/{productUuid}")
    public CartItemDto readByCartUuidAndProductUuid(@PathVariable UUID cartUuid, @PathVariable UUID productUuid) {
        return cartItemMapper.toDTO(cartItemService.findAllByCartUuidAndProductUuid(cartUuid, productUuid));
    }

    @GetMapping("/read/all/cart/{cartUuid}")
    public List<CartItemDto> readAllByCartUuid(@PathVariable UUID cartUuid) {
        return cartItemService.findAllByCartUuid(cartUuid)
                .stream()
                .map(cartItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public CartItemDto delete(@RequestParam UUID uuid) {
        CartItem deletedCartItem = cartItemService.deleteCartItem(uuid);
        return cartItemMapper.toDTO(deletedCartItem);
    }

    @DeleteMapping("/delete/all")
    public List<CartItemDto> deleteAll() {
        return cartItemService.deleteAllCartItems()
                .stream()
                .map(cartItemMapper::toDTO)
                .collect(Collectors.toList());
    }
}
