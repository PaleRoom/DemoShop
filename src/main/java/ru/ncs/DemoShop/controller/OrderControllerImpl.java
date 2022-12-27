package ru.ncs.DemoShop.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.controller.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.controller.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;
import ru.ncs.DemoShop.controller.response.GetOrderResponse;
import ru.ncs.DemoShop.service.OrderService;

@RestController
@RequestMapping("/orders")
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
    public GetCustomerResponse getOneOrder(UUID id) {
        return null;
    }

    @Override
    public UUID updateOrder(UUID id, UpdateOrderRequest updateOrderRequest) {


        return orderService.update(updateOrderRequest, id);
    }

    @Override
    public void deleteOrder(UUID id) {

    }
}
