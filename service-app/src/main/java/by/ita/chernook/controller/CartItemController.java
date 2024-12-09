package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.CartItemWebDto;
import by.ita.chernook.dto.to_web.CartWebDto;
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
@RequestMapping("/business/cart/item")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    @PostMapping("/create")
    public CartItemWebDto create(@RequestParam UUID customerUuid, @RequestParam UUID productUuid, @RequestParam int quantity) {
        CartItem insertedCartItem = cartItemService.createCartItem(customerUuid, productUuid, quantity);
        return cartItemMapper.toWebDTO(insertedCartItem);
    }

    @PostMapping("/update")
    public CartItemWebDto update(@RequestParam UUID customerUuid, @RequestParam UUID productUuid, @RequestParam int quantity) {
        CartItem updatedCartItem = cartItemService.updateCartItem(customerUuid, productUuid, quantity);
        return cartItemMapper.toWebDTO(updatedCartItem);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam UUID id) {
        cartItemService.deleteCartItem(id);
    }

//    @DeleteMapping("/delete/all")
//    public List<CartItemWebDto> deleteAll() {
//        return cartItemMapper.deleteAllCartItems()
//                .stream()
//                .map(cartItemMapper::toDatabaseDTO)
//                .collect(Collectors.toList());
//    }

    @GetMapping("/read/all/customer")
    public List<CartItemWebDto> readAll(@RequestParam UUID customerUuid) {
        return cartItemService.findAllCartItemsByCustomerUuid(customerUuid)
                .stream()
                .map(cartItemMapper::toWebDTO)
                .collect(Collectors.toList());
    }
}
