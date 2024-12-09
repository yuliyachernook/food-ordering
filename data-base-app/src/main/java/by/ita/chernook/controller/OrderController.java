package by.ita.chernook.controller;

import by.ita.chernook.dto.OrderDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.model.Order;
import by.ita.chernook.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/database/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/create")
    public OrderDto create(@RequestBody OrderDto orderDto) {
        Order cart = orderMapper.toEntity(orderDto);
        Order insertedOrder = orderService.insertOrder(cart);
        return orderMapper.toDTO(insertedOrder);
    }
}
