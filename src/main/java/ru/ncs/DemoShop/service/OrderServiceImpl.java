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
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.model.Order;
import ru.ncs.DemoShop.model.OrderStatusEnum;
import ru.ncs.DemoShop.model.OrderedProduct;
import ru.ncs.DemoShop.repository.CustomerRepository;
import ru.ncs.DemoShop.repository.OrderRepository;
import ru.ncs.DemoShop.repository.OrderedProductRepository;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.aop.LogExecutionTime;
import ru.ncs.DemoShop.service.data.OrderDTO;
import ru.ncs.DemoShop.service.immutable.orderImmutable.ImmutableUpdateOrderRequest;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final OrderedProductRepository orderedProductRepository;
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
        log.debug("Order after times set: {}", order);
        order.setOwner(Optional.of(customerRepository.findById(customerId))
                .get()
                .orElseThrow(CustomerNotFoundException::new));

        log.debug("Order before saving: {}", order.getOwner().getId());
        log.debug("Order before saving: {}", order.getOwner().getName());

        orderRepository.save(order);
        log.info("Order saved");

        return order.getId();
    }

    private OrderedProduct createOrdered(UUID productId, UUID orderId) {
        OrderedProduct ordered = new OrderedProduct();
        ordered.setOrder(Optional.of(orderRepository.findById(orderId))
                .get()
                .orElseThrow(OrderNotFoundException::new));
        ordered.getOrder().setStatus(OrderStatusEnum.EXECUTING);
        ordered.setOrderId(orderId);

        ordered.setProduct(Optional.of(productRepository.findById(productId))
                .get()
                .orElseThrow(ProductNotFoundException::new));
        ordered.setProductId(productId);
        return ordered;
    }

    @Override
    @LogExecutionTime
    public UUID update(List<ImmutableUpdateOrderRequest> requestList, UUID orderId) {

        OrderStatusEnum status = orderRepository.findById(orderId)
                .map(Order::getStatus)
                .orElseThrow(OrderNotFoundException::new);

        if (status == OrderStatusEnum.CLOSED) {
            throw new RuntimeException("This order is closed and can't be updated");
        }

        for (ImmutableUpdateOrderRequest request : requestList) {
            OrderedProduct ordered = Optional.ofNullable(orderedProductRepository
                            .findByOrderIdAndProductId(orderId, request.getProductId()))
                    .or(() -> Optional.of(createOrdered(request.getProductId(), orderId)))
                    .orElseThrow(RuntimeException::new);

            if (ordered.getProduct().isAvailability()
                    & ordered.getProduct().getAmount() >= request.getQuantity()
                    & (ordered.getQuantity() + request.getQuantity() >= 0)) {
                ordered.getOrder().setTotal(ordered.getOrder().getTotal() +
                        ordered.getProduct().getPrice() * request.getQuantity());
                ordered.getProduct().setAmount(ordered.getProduct().getAmount()
                        - request.getQuantity());

                ordered.setQuantity(ordered.getQuantity() + request.getQuantity());
                log.info("Ordered product: {}", ordered.getOrder().getId());
                log.info("Ordered product: {}", ordered.getProduct().getId());
                log.info("Ordered product: {}", ordered.getQuantity());
                orderedProductRepository.save(ordered);
                log.info("Ordered product saved");
            } else {
                throw new RuntimeException("Product unavailable or Request quantity is higher than product amount");
            }
        }

        return orderId;
    }

    @Override
    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void closeOrder(UUID id) {
        orderRepository.findById(id).ifPresentOrElse(r -> r.setStatus(OrderStatusEnum.CLOSED),
                OrderNotFoundException::new);
    }

    @Override
    public void cancelOrder(UUID id) {
        orderRepository.findById(id).ifPresentOrElse(r -> {
                    r.setStatus(OrderStatusEnum.CANCELED);
                    r.getOrderedProducts().forEach(p -> p.getProduct().setAmount(p.getProduct().getAmount() + p.getQuantity()));
                },
                OrderNotFoundException::new);
    }
}
