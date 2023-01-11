package ru.ncs.DemoShop.web.converter.orderConverter;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.web.response.GetFullOrderResponse;
import ru.ncs.DemoShop.web.response.ProductDetails;
import ru.ncs.DemoShop.service.order.data.OrderDTO;

@Slf4j
@Component
public class OrderDtoToGetFullOrderResponseConverter implements Converter<OrderDTO, GetFullOrderResponse> {
    @Override
    public GetFullOrderResponse convert(OrderDTO source) {
        GetFullOrderResponse getFullOrderResponse = GetFullOrderResponse.builder()
                .id(source.getId())
                .orderCreatedAt(source.getOrderCreatedAt())
                .orderUpdatedAt(source.getOrderUpdatedAt())
                .status(source.getStatus())
                .total(source.getTotal())
                .CustomerId(source.getOwner().getId())
                .CustomerName(source.getOwner().getName())
                .build();

        List<ProductDetails> prList = source.getOrderedProducts().stream()
                .map(p -> ProductDetails.builder()
                        .productName(p.getProduct().getName())
                        .price(p.getProduct().getPrice())
                        .quantity(p.getQuantity())
                        .build())
                .toList();

        getFullOrderResponse.setProductDetailsList(prList);

        return getFullOrderResponse;
    }
}