package ru.ncs.DemoShop.service.immutable;


import lombok.Builder;
import lombok.Data;
import lombok.Value;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

@Value
@Builder
public class ImmutableCreateProductRequest {
     String name;
    String description;
    ProductCategoryEnum category;
    double price;
    int amount;
    boolean availability;

}
