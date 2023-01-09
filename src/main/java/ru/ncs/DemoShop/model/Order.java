package ru.ncs.DemoShop.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Column(name = "order_total", nullable = false)
    private double total;

    @Column(name = "order_update", nullable = false)
    private LocalDateTime orderUpdatedAt;

    @Column(name = "order_create", nullable = false)
    private LocalDateTime orderCreatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer owner;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderedProduct> orderedProducts;
}
