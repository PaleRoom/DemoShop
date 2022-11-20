package ru.ncs.DemoShop.controller.request;

import lombok.Data;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class CreateProductRequest {

    @NotEmpty(message = "product name should  not be Empty")
    private String name;

    @NotEmpty(message = "product description should not be Empty")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    @Positive(message = "Price should be at least 0 or higher")
    private double price;

    @Positive(message = "Amount should be at least 0 or higher")
    private int amount;


}
