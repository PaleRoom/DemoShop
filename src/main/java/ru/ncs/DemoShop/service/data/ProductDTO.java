package ru.ncs.DemoShop.service.data;

import lombok.Builder;
import lombok.Value;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class ProductDTO {
    UUID id;
    String name;
    String description;
    ProductCategoryEnum category;
    double price;
    int amount;
    LocalDateTime amountUpdatedAt;
    boolean availability;
}
