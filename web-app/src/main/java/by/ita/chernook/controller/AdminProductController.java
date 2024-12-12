package by.ita.chernook.controller;

import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.mapper.ProductMapper;
import by.ita.chernook.model.Product;
import by.ita.chernook.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<ProductWebDto> products = productService.readAllProducts().stream()
                .map(productMapper::toWebDTO)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new ProductWebDto());
        return "createProduct";
    }

    @PostMapping(value = "/create")
    public String createProduct(@ModelAttribute ProductWebDto productWebDto,
                                @RequestParam("file") MultipartFile file,
                                Model model) {

        Product product = productService.createProduct(productMapper.toEntity(productWebDto), file);
        ProductWebDto productWebDto1 = productMapper.toWebDTO(product);
        model.addAttribute("product", productWebDto1);
        return "redirect:/admin/products/";
    }

    @GetMapping("/read")
    public String showReadForm() {
        return "read";
    }

    @PostMapping("/read")
    public String readProductById(String uuid, Model model) {
        ProductWebDto productWebDto = productMapper.toWebDTO(productService.readProductById(uuid));
        model.addAttribute("product", productWebDto);
        return "readResult";
    }

    @GetMapping("update")
    public String showUpdateForm(@RequestParam("uuid") String uuid, Model model) {
        ProductWebDto productWebDto =  productMapper.toWebDTO(productService.readProductById(uuid));
        model.addAttribute("product", productWebDto);
        return "updateProduct";
    }

    @PostMapping("update")
    public String updateProduct(@ModelAttribute ProductWebDto productWebDto, @RequestParam(value = "file", required = false) MultipartFile file, Model model) {
        productMapper.toWebDTO(productService.updateProduct(productMapper.toEntity(productWebDto), file));
        return "redirect:/admin/products/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String uuid) {
        productService.deleteProductById(uuid);
        return "redirect:/admin/products/";
    }
}
