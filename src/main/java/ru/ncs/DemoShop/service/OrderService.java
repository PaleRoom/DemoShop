package ru.ncs.DemoShop.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.data.OrderDTO;
import ru.ncs.DemoShop.service.immutable.orderImmutable.ImmutableUpdateOrderRequest;

@Service
public interface OrderService {
    OrderDTO findOne(UUID id);

    List<OrderDTO> findAll();

    List<OrderDTO> findCustomersOrders(UUID id);

    UUID save(UUID customerId);

    UUID update(List<ImmutableUpdateOrderRequest> request, UUID orderId);

    void delete(UUID id);

    void closeOrder(UUID id);

    void cancelOrder(UUID id);
}
