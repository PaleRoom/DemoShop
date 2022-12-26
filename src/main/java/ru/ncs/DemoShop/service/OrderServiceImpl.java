package ru.ncs.DemoShop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.exception.customerException.CustomerNotFoundException;
import ru.ncs.DemoShop.exception.orderException.OrderNotFoundException;
import ru.ncs.DemoShop.model.Order;
import ru.ncs.DemoShop.model.OrderStatusEnum;
import ru.ncs.DemoShop.repository.CustomerRepository;
import ru.ncs.DemoShop.repository.OrderRepository;
import ru.ncs.DemoShop.service.data.OrderDTO;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ConversionService conversionService;
    private final CustomerRepository customerRepository;

    @Override
    public OrderDTO findOne(UUID id) {
        Optional<Order> foundOrder = orderRepository.findById(id);

        return conversionService.convert(foundOrder.orElseThrow(() ->
                new OrderNotFoundException("OrderCustomer with id not found: "
                        + id.toString())), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> list = orderRepository.findAll();
        List<OrderDTO> listDTO = new ArrayList<>();
        for (Order order : list) {
            listDTO.add(conversionService.convert(order, OrderDTO.class));
        }

        return listDTO;
    }

    @Override
    public List<OrderDTO> findCustomersOrders(UUID id) {
        List<Order> list = orderRepository.findByOwnerId(id);
        List<OrderDTO> listDTO = new ArrayList<>();
        for (Order order : list) {
            listDTO.add(conversionService.convert(order, OrderDTO.class));
        }

        return listDTO;
    }

    @Override
    public UUID save(UUID customerId) {
        Order order = new Order();

        order.setOrderCreatedAt(LocalDateTime.now());
        order.setOrderUpdatedAt(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.OPENED);
        log.debug("Order after times set: {}",order);

        order.setOwner(Optional.of(customerRepository.findById(customerId))
                .get()
                .orElseThrow(CustomerNotFoundException::new));

        log.debug("Order before saving: {}",order.getOwner().getId());
        log.debug("Order before saving: {}",order.getOwner().getName());
        log.debug("Order before saving: {}",order.getOwner().getEmail());
        log.debug("Order before saving: {}",order.getOwner().getPhoneNumber());
        orderRepository.save(order);
        log.info("Order saved");

        return order.getId();
    }

    @Override
    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }
}
