package ru.ncs.DemoShop.web;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.repository.RedisRepository;
import ru.ncs.DemoShop.web.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.web.response.GetFullOrderResponse;
import ru.ncs.DemoShop.web.response.GetOrderResponse;
import ru.ncs.DemoShop.service.order.OrderService;
import ru.ncs.DemoShop.service.order.immutable.ImmutableUpdateOrderRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final ConversionService conversionService;

    private final RedisRepository redisRepository;

    @Override
    public UUID createOrder(UUID id) {
        redisRepository.addIdpKey("da1d2b7c-3eb6-4bbf-9463-fd87a1a669ab", id.toString());
        log.info("/////////////// WE HAVE GOT A DATA!  {}",
                redisRepository.getIdpKey("da1d2b7c-3eb6-4bbf-9463-fd87a1a669ab"));
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
