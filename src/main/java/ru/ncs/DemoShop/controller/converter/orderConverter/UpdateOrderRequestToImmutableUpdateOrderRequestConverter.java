package ru.ncs.DemoShop.controller.converter.orderConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.service.immutable.orderImmutable.ImmutableUpdateOrderRequest;

@Component
public class UpdateOrderRequestToImmutableUpdateOrderRequestConverter implements Converter<UpdateOrderRequest, ImmutableUpdateOrderRequest> {
    @Override
    public ImmutableUpdateOrderRequest convert(UpdateOrderRequest source) {
        return ImmutableUpdateOrderRequest.builder()
                .productId(source.getProductId())
                .quantity(source.getQuantity())
                .build();
    }
}
