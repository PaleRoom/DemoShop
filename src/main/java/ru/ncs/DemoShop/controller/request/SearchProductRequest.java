package ru.ncs.DemoShop.controller.request;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SearchProductRequest {
    String name;
    Double price;
    Integer amount;
    Boolean availability;
}
