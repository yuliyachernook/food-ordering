package by.ita.chernook.controller;

import by.ita.chernook.dto.CustomerWebDto;
import by.ita.chernook.mapper.CustomerMapper;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Customer;
import by.ita.chernook.service.CustomerService;
import by.ita.chernook.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new CustomerWebDto());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute CustomerWebDto userWebDto, Model model) {
        try {  customerService.createCustomer(customerMapper.toEntity(userWebDto)); }
        catch (Exception e) {
            if (e.getMessage().contains("User with this login already exists")) {
                model.addAttribute("user", new CustomerWebDto());

                    model.addAttribute("error", true);
                return "registration";
            }
        }
        return "redirect:/login";
    }
}