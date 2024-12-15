package by.ita.chernook.controller;

import by.ita.chernook.dto.CouponWebDto;
import by.ita.chernook.dto.ProductWebDto;
import by.ita.chernook.mapper.CouponMapper;
import by.ita.chernook.model.Coupon;
import by.ita.chernook.service.CouponService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<CouponWebDto> coupons = couponService.readAllCoupons().stream()
                .map(couponMapper::toWebDTO)
                .collect(Collectors.toList());
        model.addAttribute("coupons", coupons);
        return "coupons";
    }

    @GetMapping("/create")
    public String showCreateGlobalCouponPage(Model model) {
        model.addAttribute("coupon", new CouponWebDto());
        return "createCoupon";
    }

    @PostMapping(value = "/create")
    public String createCoupon(@ModelAttribute CouponWebDto couponWebDto, Model model) {
        Coupon createdCoupon = couponService.createCoupon(couponMapper.toEntity(couponWebDto));
        CouponWebDto coupon = couponMapper.toWebDTO(createdCoupon);
        model.addAttribute("coupon", coupon);
        return "redirect:/admin/coupons/";
    }

    @GetMapping("update")
    public String showUpdateForm(@RequestParam("uuid") String uuid, Model model) {
        CouponWebDto couponWebDto =  couponMapper.toWebDTO(couponService.readCouponById(uuid));
        model.addAttribute("coupon", couponWebDto);
        return "updateCoupon";
    }

    @PostMapping("update")
    public String updateCoupon(@ModelAttribute CouponWebDto couponWebDto, Model model) {
        couponMapper.toWebDTO(couponService.updateCoupon(couponMapper.toEntity(couponWebDto)));
        return "redirect:/admin/coupons/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String uuid) {
        couponService.deleteCouponById(uuid);
        return "redirect:/admin/coupons/";
    }
}
