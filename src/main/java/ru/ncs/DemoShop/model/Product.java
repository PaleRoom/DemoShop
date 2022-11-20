package ru.ncs.DemoShop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
    @Getter
        @Setter
            @NoArgsConstructor
                @AllArgsConstructor
                    @ToString
                        @Builder
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "product_name", nullable = false)
    @NotEmpty(message = "product name should be not Empty")
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    @Column(name = "product_price", nullable = false)
    @Positive(message = "Price should be at least 0 or higher")
    private double price;

    @Column(name = "product_amount", nullable = false)
    @Positive(message = "Amount should be at least 0 or higher")
    private int amount;

    @Column(name = "amount_update", nullable = false)
    private LocalDateTime amountUpdatedAt;


}
