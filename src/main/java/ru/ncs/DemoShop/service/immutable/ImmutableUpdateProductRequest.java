package ru.ncs.DemoShop.service.immutable;

import lombok.Builder;
import lombok.Value;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import java.util.UUID;

@Value
@Builder
public class ImmutableUpdateProductRequest {
    UUID id;
    String name;
    String description;
    ProductCategoryEnum category;
    double price;
    int amount;
}
