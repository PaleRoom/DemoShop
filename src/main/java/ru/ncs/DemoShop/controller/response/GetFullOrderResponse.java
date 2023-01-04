package ru.ncs.DemoShop.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import ru.ncs.DemoShop.model.OrderStatusEnum;

@Data
@Builder
public class GetFullOrderResponse {
    UUID id;
    OrderStatusEnum status;
    double total;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    LocalDateTime orderUpdatedAt;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    LocalDateTime orderCreatedAt;
    String CustomerName;
    UUID CustomerId;
    List<ProductInOrder> productInOrderList;
}
