package by.ita.chernook.util;

import by.ita.chernook.dto.enums.CategoryEnum;
import by.ita.chernook.dto.enums.CouponTypeEnum;
import by.ita.chernook.dto.enums.OrderStatusEnum;
import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.model.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public abstract class TestUtils {

    protected User buildUser(UUID uuid, String login, String password, UserRoleEnum userRole, ZonedDateTime creationDateTime) {
        return User.builder()
                .uuid(uuid)
                .login(login)
                .password(password)
                .userRoleEnum(userRole)
                .creationDateTime(creationDateTime)
                .build();
    }

    protected Product buildProduct(UUID uuid, String itemName, BigDecimal price, String description, CategoryEnum category, int discountPercentage,
                                   ZonedDateTime creationDateTime) {
        return Product.builder()
                .uuid(uuid)
                .itemName(itemName)
                .price(price)
                .description(description)
                .category(category)
                .discountPercentage(discountPercentage)
                .creationDateTime(creationDateTime)
                .build();
    }

    protected Order buildOrder(UUID uuid, List<OrderItem> orderItems, DeliveryAddress deliveryAddress, Customer customer, OrderStatusEnum orderStatus,
                               String comment, BigDecimal totalPrice, ZonedDateTime creationDateTime) {
        return Order.builder()
                .uuid(uuid)
                .orderItems(orderItems)
                .deliveryAddress(deliveryAddress)
                .customer(customer)
                .orderStatus(orderStatus)
                .comment(comment)
                .totalPrice(totalPrice)
                .creationDateTime(creationDateTime)
                .build();
    }

    protected OrderItem buildOrderItem(UUID uuid, Product product, BigDecimal price, int quantity) {
        return OrderItem.builder()
                .uuid(uuid)
                .product(product)
                .price(price)
                .quantity(quantity)
                .build();
    }

    protected DeliveryAddress buildDeliveryAddress(UUID uuid, String city, String street, String house, Integer apartment) {
        return DeliveryAddress.builder()
                .uuid(uuid)
                .city(city)
                .street(street)
                .house(house)
                .apartment(apartment)
                .build();
    }

    protected Customer buildCustomer(UUID uuid, String firstName, String lastName, String phoneNumber, String email, User user,
                                     List<DeliveryAddress> deliveryAddresses, Cart cart) {
        return Customer.builder()
                .uuid(uuid)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .email(email)
                .user(user)
                .deliveryAddresses(deliveryAddresses)
                .cart(cart)
                .build();
    }

    protected Coupon buildCoupon(UUID uuid, String name, String description, String code, CouponTypeEnum couponType, int discount,
                                 int availableUses) {
        return Coupon.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .code(code)
                .couponType(couponType)
                .discount(discount)
                .availableUses(availableUses)
                .build();
    }

    protected Cart buildCart(UUID uuid, ZonedDateTime creationDateTime) {
        return Cart.builder()
                .uuid(uuid)
                .creationDateTime(creationDateTime)
                .build();
    }

    protected CartItem buildCartItem(UUID uuid, Cart cart, Product product, int quantity) {
        return CartItem.builder()
                .uuid(uuid)
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
    }
}
