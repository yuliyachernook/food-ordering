package by.ita.chernook.model;

import by.ita.chernook.dto.enums.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String login;
    private String password;
    @Column(updatable = false)
    private ZonedDateTime creationDateTime;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRoleEnum;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Customer customer;
}
