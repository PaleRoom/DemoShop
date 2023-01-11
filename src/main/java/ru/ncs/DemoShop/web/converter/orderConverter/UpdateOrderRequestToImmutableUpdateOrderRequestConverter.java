package ru.ncs.DemoShop.web.converter.orderConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.web.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.service.order.immutable.ImmutableUpdateOrderRequest;

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
