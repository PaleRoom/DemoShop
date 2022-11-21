package ru.ncs.DemoShop.controller.request;

import lombok.Value;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Value
public class UpdateProductRequest {
    @NotBlank(message = "product name should  not be Empty")
    String name;

    @NotBlank(message = "product description should not be Empty")
    String description;

    @Enumerated(EnumType.STRING)
    ProductCategoryEnum category;

    @Positive(message = "Price should be at least 0 or higher")
    double price;

    @Positive(message = "Amount should be at least 0 or higher")
    int amount;
}
