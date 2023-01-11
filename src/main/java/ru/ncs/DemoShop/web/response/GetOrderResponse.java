package ru.ncs.DemoShop.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import ru.ncs.DemoShop.model.OrderStatusEnum;

@Data
@Builder
public class GetOrderResponse {
    private UUID id;
    private OrderStatusEnum status;
    private  double total;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    private  LocalDateTime orderUpdatedAt;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    private LocalDateTime orderCreatedAt;

    private String CustomerName;
    private UUID CustomerId;
}
