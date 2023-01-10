package ru.ncs.DemoShop.service.product.immutable;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

@Value
@Builder
public class ImmutableUpdateProductRequest {
    String name;
    ProductCategoryEnum category;
    Double price;
    Integer amount;

    @Nullable
    Boolean availability;

    @Nullable
    String description;
}
