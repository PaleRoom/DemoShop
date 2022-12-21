package ru.ncs.DemoShop.service.immutable;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Positive;
import org.springframework.lang.Nullable;

@Builder
@Value
public class ImmutableSearchProductRequest {
    @Nullable
    String name;

    @Nullable
    Double price;

    @Nullable
    Integer amount;

    @Nullable
    Boolean availability;
}
