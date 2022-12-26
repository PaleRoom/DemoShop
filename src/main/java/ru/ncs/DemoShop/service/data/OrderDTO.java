package ru.ncs.DemoShop.service.data;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.model.OrderStatusEnum;

@Value
@Builder
public class OrderDTO {
    UUID id;
    OrderStatusEnum status;
    double total;
    LocalDateTime orderUpdatedAt;
    LocalDateTime orderCreatedAt;
    Customer owner;
}
