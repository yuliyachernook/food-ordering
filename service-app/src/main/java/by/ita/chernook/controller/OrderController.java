package by.ita.chernook.controller;

import by.ita.chernook.dto.to_web.OrderWebDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Order;
import by.ita.chernook.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/business/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/create")
    public OrderWebDto create(@RequestBody OrderWebDto orderWebDto, @RequestParam UUID customerUuid) {
        Order insertedOrder = orderService.createOrder(orderMapper.toEntity(orderWebDto), customerUuid);
        return orderMapper.toWebDTO(insertedOrder);
    }

    @PostMapping("/update")
    public OrderWebDto update(@RequestBody OrderWebDto orderWebDto) {
        Order updatedOrder = orderService.updateOrder(orderMapper.toEntity(orderWebDto));
        return orderMapper.toWebDTO(updatedOrder);
    }

    @PostMapping("/build")
    public OrderWebDto build(@RequestParam UUID customerUuid) {
        Order insertedOrder = orderService.buildOrderFromCart(customerUuid);
        return orderMapper.toWebDTO(insertedOrder);
    }

    @GetMapping("/read")
    public OrderWebDto read(@RequestParam UUID uuid) {
        Order order = orderService.findOrderById(uuid);
        return orderMapper.toWebDTO(order);
    }

    @GetMapping("/read/all")
    public List<OrderWebDto> readAll() {
        return orderService.findAll()
                .stream()
                .map(orderMapper::toWebDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/all/customer/{customerUuid}")
    public List<OrderWebDto> readAllByCustomerUuid(@PathVariable UUID customerUuid) {
        return orderService.findAllByCustomerUuid(customerUuid)
                .stream()
                .map(orderMapper::toWebDTO)
                .collect(Collectors.toList());
    }
}
