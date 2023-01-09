package ru.ncs.DemoShop.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.controller.request.customerRequest.CreateCustomerRequest;
import ru.ncs.DemoShop.controller.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;
import ru.ncs.DemoShop.controller.response.GetOrderResponse;
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.service.CustomerService;
import ru.ncs.DemoShop.service.OrderService;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableCreateCustomerRequest;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableUpdateCustomerRequest;


@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    @GetMapping
    public List<GetCustomerResponse> getCustomers() {
        return customerService.findAll().stream()
                .map(dto -> conversionService.convert(dto, GetCustomerResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetCustomerResponse getOneCustomer(UUID id) {
        return conversionService.convert(customerService.findOne(id), GetCustomerResponse.class);
    }

    @Override
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(CreateCustomerRequest createCustomerRequest) {
        ImmutableCreateCustomerRequest immutableCreateCustomerRequest = conversionService.convert(createCustomerRequest, ImmutableCreateCustomerRequest.class);

        return customerService.save(immutableCreateCustomerRequest);
    }

    @Override
    public UUID updateCustomer(UUID id, UpdateCustomerRequest updateCustomerRequest) {
        ImmutableUpdateCustomerRequest immutableUpdateCustomerRequest =
                conversionService.convert(updateCustomerRequest, ImmutableUpdateCustomerRequest.class);

        return customerService.update(immutableUpdateCustomerRequest, id).getId();
    }

    @Override
    public void deleteCustomer(UUID id) {
        if (customerService.findOne(id) == null) {
            throw new ProductNotFoundException();
        }
        customerService.delete(id);
    }

    @Override
    public UUID createOrder(UUID id) {
            return orderService.save(id);
    }

    @Override
    public List<GetOrderResponse> getCustomersOrders (UUID id) {

        return orderService.findCustomersOrders(id).stream()
                .map(dto -> conversionService.convert(dto, GetOrderResponse.class))
                .collect(Collectors.toList()); }

    //TODO реализовать поиск
}
