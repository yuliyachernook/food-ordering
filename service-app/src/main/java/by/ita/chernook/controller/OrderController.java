package by.ita.chernook.controller;


import by.ita.chernook.dto.to_web.OrderWebDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Order;
import by.ita.chernook.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/business/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;


    @PostMapping("/create")
    public OrderWebDto create(@RequestParam UUID customerUuid) {
        Order insertedOrder = orderService.createOrderFromCart(customerUuid);
        return orderMapper.toWebDTO(insertedOrder);
    }

}
