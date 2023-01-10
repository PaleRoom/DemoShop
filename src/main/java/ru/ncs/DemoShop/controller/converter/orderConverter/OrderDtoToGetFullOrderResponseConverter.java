package ru.ncs.DemoShop.controller.converter.orderConverter;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetFullOrderResponse;
import ru.ncs.DemoShop.controller.response.ProductDetails;
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

        List<ProductDetails> prList = new ArrayList<>();

        source.getOrderedProducts().forEach(p -> {
            ProductDetails pr = ProductDetails.builder()
                    .productName(p.getProduct().getName())
                    .price(p.getProduct().getPrice())
                    .quantity(p.getQuantity())
                    .build();
            prList.add(pr);
        });
        getFullOrderResponse.setProductDetailsList(prList);

        return getFullOrderResponse;
    }
}