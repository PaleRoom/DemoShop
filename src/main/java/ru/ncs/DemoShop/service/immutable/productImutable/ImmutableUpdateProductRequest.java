package ru.ncs.DemoShop.service.immutable.productImutable;

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
