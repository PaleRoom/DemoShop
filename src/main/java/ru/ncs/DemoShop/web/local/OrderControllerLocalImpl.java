package ru.ncs.DemoShop.web.local;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.service.order.OrderService;
import ru.ncs.DemoShop.service.order.immutable.ImmutableUpdateOrderRequest;
import ru.ncs.DemoShop.web.OrderController;
import ru.ncs.DemoShop.web.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.web.response.GetFullOrderResponse;
import ru.ncs.DemoShop.web.response.GetOrderResponse;

@Slf4j
@Profile("local")
@RestController
@RequiredArgsConstructor
public class OrderControllerLocalImpl implements OrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    public UUID createOrder(UUID id, HttpServletRequest request) {
        return orderService.save(id);
    }

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
    public List<GetOrderResponse> getCustomersOrders(UUID id) {
        return orderService.findCustomersOrders(id).stream()
                .map(dto -> conversionService.convert(dto, GetOrderResponse.class))
                .collect(Collectors.toList());
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

