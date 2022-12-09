package ru.ncs.DemoShop.service.immutable;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

@Value
@Builder
public class ImmutableUpdateProductRequest {
    String name;
    String description;
    ProductCategoryEnum category;
    Double price;
    Integer amount;
    Boolean availability;
}
