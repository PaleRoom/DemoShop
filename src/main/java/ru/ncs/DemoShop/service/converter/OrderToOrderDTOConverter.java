package ru.ncs.DemoShop.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.model.Order;
import ru.ncs.DemoShop.service.order.data.OrderDTO;

@Component
public class OrderToOrderDTOConverter implements Converter <Order, OrderDTO> {
    @Override
    public OrderDTO convert(Order source) {
        return OrderDTO.builder()
                .id(source.getId())
                .orderCreatedAt(source.getOrderCreatedAt())
                .orderUpdatedAt(source.getOrderUpdatedAt())
                .owner(source.getOwner())
                .orderedProducts(source.getOrderedProducts())
                .status(source.getStatus())
                .total(source.getTotal())
                .build();
    }
}
