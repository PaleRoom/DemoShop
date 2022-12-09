package ru.ncs.DemoShop.controller.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Positive;
import lombok.Value;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

@Value
public class UpdateProductRequest {
    String name;
    String description;

    @Enumerated(EnumType.STRING)
    ProductCategoryEnum category;

    @Positive(message = "Price should be at least 0 or higher")
    Double price;

    @Positive(message = "Amount should be at least 0 or higher")
    Integer amount;
}
