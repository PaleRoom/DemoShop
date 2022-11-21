package ru.ncs.DemoShop.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetProductResponse {
    private UUID id;
    private String name;
    private String description;
    private ProductCategoryEnum category;
    private double price;
    private int amount;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    private LocalDateTime amountUpdatedAt;
}
