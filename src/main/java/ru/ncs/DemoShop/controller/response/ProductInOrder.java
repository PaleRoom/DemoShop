package ru.ncs.DemoShop.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInOrder {
    String productName;
    double price;
    int quantity;
}
