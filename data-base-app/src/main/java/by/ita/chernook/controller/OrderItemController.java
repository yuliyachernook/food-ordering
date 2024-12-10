package by.ita.chernook.controller;

import by.ita.chernook.dto.OrderItemDto;
import by.ita.chernook.mapper.OrderItemMapper;
import by.ita.chernook.model.OrderItem;
import by.ita.chernook.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/database/order/item")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;

    @PostMapping("/create")
    public OrderItemDto create(@RequestBody OrderItemDto orderDto) {
        OrderItem orderItem = orderItemMapper.toEntity(orderDto);
        OrderItem insertedOrderItem = orderItemService.insertOrderItem(orderItem);
        return orderItemMapper.toDTO(insertedOrderItem);
    }

    @PutMapping("/update")
    public OrderItemDto update(@RequestBody OrderItemDto userDto) {
        OrderItem orderItem = orderItemMapper.toEntity(userDto);
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItem);
        return orderItemMapper.toDTO(updatedOrderItem);
    }

    @GetMapping("/read")
    public OrderItemDto read(@RequestParam UUID uuid) {
        OrderItem orderItem = orderItemService.findOrderItemById(uuid);
        return orderItemMapper.toDTO(orderItem);
    }

    @GetMapping("/read/all/order/{orderUuid}")
    public List<OrderItemDto> readAllByCustomerUuid(@PathVariable UUID orderUuid) {
        return orderItemService.findAllByOrderUuid(orderUuid)
                .stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public OrderItemDto delete(@RequestParam UUID uuid) {
        OrderItem orderItem = orderItemService.deleteOrderItem(uuid);
        return orderItemMapper.toDTO(orderItem);
    }
}
