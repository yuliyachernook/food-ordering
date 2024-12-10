package by.ita.chernook.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String city;
    private String street;
    private String house;
    private Integer apartment;

    @ManyToOne
    @JoinColumn(name = "customerUuid")
    @ToString.Exclude
    private Customer customer;
}
