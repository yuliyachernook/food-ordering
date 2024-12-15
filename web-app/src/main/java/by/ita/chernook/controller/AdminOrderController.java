package by.ita.chernook.controller;

import by.ita.chernook.dto.OrderWebDto;
import by.ita.chernook.mapper.OrderMapper;
import by.ita.chernook.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<OrderWebDto> orders = orderService.readAllOrders().stream()
                .map(orderMapper::toWebDTO)
                .collect(Collectors.toList());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("update")
    public String showUpdateForm(@RequestParam("uuid") String uuid, Model model) {
        OrderWebDto orderWebDto =  orderMapper.toWebDTO(orderService.readOrderById(uuid));
        model.addAttribute("order", orderWebDto);
        return "updateOrder";
    }

    @PostMapping("update")
    public String updateOrder(@ModelAttribute OrderWebDto orderWebDto, Model model) {
        orderMapper.toWebDTO(orderService.updateOrder(orderMapper.toEntity(orderWebDto)));
        return "redirect:/admin/orders/";
    }
}
