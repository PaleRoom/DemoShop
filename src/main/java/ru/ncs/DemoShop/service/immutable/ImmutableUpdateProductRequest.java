package ru.ncs.DemoShop.service.immutable;

import lombok.Builder;
import lombok.Data;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import java.util.UUID;

@Data
@Builder
public class ImmutableUpdateProductRequest {
    private UUID id;
    private String name;
    private String description;
    private ProductCategoryEnum category;
    private double price;
    private int amount;
}
