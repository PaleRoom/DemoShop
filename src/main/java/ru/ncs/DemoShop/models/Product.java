package ru.ncs.DemoShop.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue


    private UUID id;

    @Column(name = "product_name")
    @NotEmpty(message = "product name should be not Empty")
    private String name;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_category")
    private String category;

    @Column (name = "product_price")
    @Min(value = 0, message = "Price should be at least 0 or higher")
    private double price;

    @Column (name = "product_amount")
    @Min(value = 0, message = "Amount should be at least 0 or higher")
    private int amount;

    @Column (name="amount_update")
    private LocalDateTime amountUpdatedAt;

    public Product() {};

    public Product(String name, String description, String category, double price, int amount) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.amount = amount;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmountUpdatedAt(LocalDateTime amountUpdatedAt) {
        this.amountUpdatedAt = amountUpdatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getAmountUpdatedAt() {
        return amountUpdatedAt;
    }



}
