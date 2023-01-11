package ru.ncs.DemoShop.web;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.web.request.customerRequest.CreateCustomerRequest;
import ru.ncs.DemoShop.web.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.web.response.GetCustomerResponse;
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.service.customer.CustomerService;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableCreateCustomerRequest;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableUpdateCustomerRequest;


@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;
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
}
