package ru.ncs.DemoShop.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetails {
    private  String productName;
    private  double price;
    private int quantity;
}
