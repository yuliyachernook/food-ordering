package by.ita.chernook.controller;

import by.ita.chernook.dto.OrderDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.model.Order;
import by.ita.chernook.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PutMapping("/update")
    public OrderDto update(@RequestBody OrderDto userDto) {
        Order order = orderMapper.toEntity(userDto);
        Order updatedOrder = orderService.updateOrder(order);
        return orderMapper.toDTO(updatedOrder);
    }

    @GetMapping("/read")
    public OrderDto read(@RequestParam UUID uuid) {
        Order order = orderService.findOrderById(uuid);
        return orderMapper.toDTO(order);
    }

    @GetMapping("/read/all")
    public List<OrderDto> readAll() {
        return orderService.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/read/all/customer/{customerUuid}")
    public List<OrderDto> readAllByCustomerUuid(@PathVariable UUID customerUuid) {
        return orderService.findAllByCustomerUuid(customerUuid)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public OrderDto delete(@RequestParam UUID uuid) {
        Order order = orderService.deleteOrder(uuid);
        return orderMapper.toDTO(order);
    }
}
