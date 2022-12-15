package ru.ncs.DemoShop.controller.request;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;

@Builder
@Data
public class SearchProductRequest {
    String name;
    Double price;
    Integer amount;
    Boolean availability;
}
