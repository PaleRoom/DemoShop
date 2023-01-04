package ru.ncs.DemoShop.service.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.model.OrderStatusEnum;
import ru.ncs.DemoShop.model.OrderedProduct;

@Value
@Builder
public class OrderDTO {
    UUID id;
    OrderStatusEnum status;
    double total;
    LocalDateTime orderUpdatedAt;
    LocalDateTime orderCreatedAt;
    Customer owner;
    List<OrderedProduct> orderedProducts;
}
