package ru.ncs.DemoShop.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.data.OrderDTO;
import ru.ncs.DemoShop.service.data.ProductDTO;

@Service
public interface OrderService {
    OrderDTO findOne(UUID id);

     List<OrderDTO> findAll();

    List<OrderDTO> findCustomersOrders(UUID id);

    UUID save(UUID customerId);

    //ProductDTO update(ImmutableUpdateProductRequest request, UUID id);

    void delete(UUID id);
 }
