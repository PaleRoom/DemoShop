package ru.ncs.DemoShop.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetails {
    private String productName;
    private double price;
    private int quantity;
}
