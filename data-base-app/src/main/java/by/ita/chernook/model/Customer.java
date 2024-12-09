package by.ita.chernook.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private ZonedDateTime creationDateTime;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userUuid")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<DeliveryAddress> deliveryAddresses;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cartUuid")
    private Cart cart;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customersCoupons",
            joinColumns = @JoinColumn(name = "couponUuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "customerUuid", referencedColumnName = "uuid")
    )
    private List<Coupon> coupons;
}