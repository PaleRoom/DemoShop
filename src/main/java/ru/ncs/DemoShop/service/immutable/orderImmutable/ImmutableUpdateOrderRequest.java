package ru.ncs.DemoShop.service.immutable.orderImmutable;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImmutableUpdateOrderRequest {
    UUID productId;
    int quantity;
}
