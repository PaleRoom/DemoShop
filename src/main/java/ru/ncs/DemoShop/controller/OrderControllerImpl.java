package ru.ncs.DemoShop.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.controller.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;
import ru.ncs.DemoShop.controller.response.GetFullOrderResponse;
import ru.ncs.DemoShop.controller.response.GetOrderResponse;
import ru.ncs.DemoShop.service.OrderService;
import ru.ncs.DemoShop.service.immutable.orderImmutable.ImmutableUpdateOrderRequest;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController{
    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    public List<GetOrderResponse> getOrders() {
        return orderService.findAll().stream()
                .map(dto -> conversionService.convert(dto, GetOrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetFullOrderResponse getOneOrder(UUID id) {
        return conversionService.convert(orderService.findOne(id), GetFullOrderResponse.class);
    }

    @Override
    public UUID updateOrder(UUID id, List<UpdateOrderRequest> updateOrderRequest) {
       List<ImmutableUpdateOrderRequest> imReqList = updateOrderRequest.stream()
                .map(req -> conversionService.convert(req, ImmutableUpdateOrderRequest.class))
                .collect(Collectors.toList());
        return orderService.update(imReqList, id);
    }

    @Override
    public void deleteOrder(UUID id) {
    }

    @Override
    public void closeOrder(UUID id) {
        orderService.closeOrder(id);
    }

    @Override
    public void cancelOrder(UUID id) {
        orderService.cancelOrder(id);
    }
}
