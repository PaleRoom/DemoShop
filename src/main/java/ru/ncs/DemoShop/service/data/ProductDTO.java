package ru.ncs.DemoShop.service.data;

import lombok.Builder;
import lombok.Data;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private ProductCategoryEnum category;
    private double price;
    private int amount;
    private LocalDateTime amountUpdatedAt;
}
