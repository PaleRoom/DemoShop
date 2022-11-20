package ru.ncs.DemoShop.service.immutable;


import lombok.Builder;
import lombok.Data;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

@Data
@Builder
public class ImmutableCreateProductRequest {
    private String name;
    private String description;
    private ProductCategoryEnum category;
    private double price;
    private int amount;
}
