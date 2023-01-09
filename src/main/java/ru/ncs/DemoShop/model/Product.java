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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "product_name", nullable = false)
    @NotBlank(message = "Поле name не должно быть пустым")
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    @Column(name = "product_price", nullable = false)
    @Positive(message = "Значение price должно быть от 0 и выше")
    private double price;

    @Column(name = "product_amount", nullable = false)
    @Positive(message = "Значение amount должно быть от 0 и выше")
    private int amount;

    @Column(name = "amount_update", nullable = false)
    private LocalDateTime amountUpdatedAt;

    @Column(name = "availability", nullable = false)
    private boolean availability;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<OrderedProduct> orderedProducts;


}
