package ru.ncs.DemoShop.service.immutable;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Positive;

@Builder
@Value
public class ImmutableSearchProductRequest {

    String name;
    Double price;
    Integer amount;
    Boolean availability;
}
