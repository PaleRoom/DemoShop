package ru.ncs.DemoShop.controller.converter.orderConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetOrderResponse;
import ru.ncs.DemoShop.service.data.OrderDTO;

@Component
public class OrderDtoToGetOrderResponseConverter implements Converter<OrderDTO, GetOrderResponse> {
    @Override
    public GetOrderResponse convert(OrderDTO source) {
        return GetOrderResponse.builder()
                .id(source.getId())
                .orderCreatedAt(source.getOrderCreatedAt())
                .orderUpdatedAt(source.getOrderUpdatedAt())
                .status(source.getStatus())
                .total(source.getTotal())
                .CustomerId(source.getOwner().getId())
                .CustomerName(source.getOwner().getName())
                .build();

    }
}