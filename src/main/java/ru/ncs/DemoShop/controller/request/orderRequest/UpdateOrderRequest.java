package ru.ncs.DemoShop.controller.request.orderRequest;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateOrderRequest {
    @NotNull(message = "Product field must not be Null!")
    UUID productId;
    @NotNull(message = "Quantity field must not be Empty!")
    int quantity;
}
