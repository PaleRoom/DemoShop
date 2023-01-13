package ru.ncs.DemoShop.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.service.idempotence.IdempotenceService;
import ru.ncs.DemoShop.service.order.OrderService;
import ru.ncs.DemoShop.service.order.immutable.ImmutableUpdateOrderRequest;
import ru.ncs.DemoShop.web.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.web.response.GetFullOrderResponse;
import ru.ncs.DemoShop.web.response.GetOrderResponse;

@Slf4j
@Profile("default")
@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;
    private final IdempotenceService idempotenceService;

    @Override
    public UUID createOrder(UUID id, HttpServletRequest request) {
        String headerKey = request.getHeader("IdempotenceKey");
        log.info("HEADER KEY: {}", headerKey);
        if (Optional.ofNullable(idempotenceService.getIdpKey(headerKey)).isPresent()) {
            return UUID.fromString(idempotenceService.getIdpKey(headerKey));
        } else {
            return UUID.fromString(idempotenceService.addIdpKey(headerKey, orderService.save(id).toString()));
        }
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
