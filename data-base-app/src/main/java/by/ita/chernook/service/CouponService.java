package by.ita.chernook.service;

//@Service
public class CouponService {

//    @Autowired
//    private CouponRepository couponRepository;
//
//    public Coupon createGlobalCoupon(Coupon coupon) {
//        coupon.setGlobal(true);
//        return couponRepository.save(coupon);
//    }
//
//    public Coupon createPersonalizedCoupon(Long userId, Coupon coupon) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User  not found"));
//        coupon.setUser (user);
//        coupon.setGlobal(false);
//        return couponRepository.save(coupon);
//    }
//
//    public List<Coupon> getAllCoupons() {
//        return couponRepository.findAll();
//    }
//
//    public List<Coupon> getGlobalCoupons() {
//        return couponRepository.findByIsGlobal(true);
//    }
//
//    public Coupon getCouponByCode(String code) {
//        return couponRepository.findByCode(code)
//                .orElseThrow(() -> new RuntimeException("Coupon not found"));
//    }
}