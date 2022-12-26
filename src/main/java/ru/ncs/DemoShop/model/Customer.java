package ru.ncs.DemoShop.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "customer_name", nullable = false)
    @NotEmpty(message = "customer name should be not Empty")
    private String name;

    @Column(name = "customer_surname", nullable = false)
    @NotEmpty(message = "customer surname should be not Empty")
    private String surname;

    @Column(name = "customer_patronymic", nullable = false)
    @NotEmpty(message = "customer patronymic  should be not Empty")
    private String patronymic;

    @Column(name = "customer_email", nullable = false)
    @Email
    @NotEmpty(message = "customer email should be not Empty")
    private String email;

    @Column(name = "customer_phone_number", nullable = false)
    @NotEmpty(message = "customer phone number should be not Empty")
    private String phoneNumber;

    @Column(name = "customer_update", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Order> orders;
}
