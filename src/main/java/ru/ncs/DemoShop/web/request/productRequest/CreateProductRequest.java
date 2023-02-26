package ru.ncs.DemoShop.web.request.productRequest;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Required;
import ru.ncs.DemoShop.model.ProductCategoryEnum;

@Value
@Builder
@Jacksonized
public class CreateProductRequest {
    @NotBlank(message = "product name should  not be Empty")
    String name;

    @NotBlank(message = "product description should not be Empty")
    String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    ProductCategoryEnum category;

    @Positive(message = "Price should be at least 0 or higher")
    double price;

    @Positive(message = "Amount should be at least 0 or higher")
    int amount;

    @NotNull
    boolean availability;
}
