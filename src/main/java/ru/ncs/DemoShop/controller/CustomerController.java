package ru.ncs.DemoShop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncs.DemoShop.controller.request.customerRequest.CreateCustomerRequest;
import ru.ncs.DemoShop.controller.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;
import ru.ncs.DemoShop.controller.response.GetOrderResponse;

@RequestMapping("/default")
@Tag(name = "Customer service", description = "the Customer API with description tag annotation")
public interface CustomerController {
    @GetMapping
    List<GetCustomerResponse> getCustomers();

    @GetMapping("/{id}")
    GetCustomerResponse getOneCustomer(@PathVariable("id") UUID id);

    @PostMapping
    UUID create(@RequestBody @Valid CreateCustomerRequest createCustomerRequest);

    @PutMapping("/{id}")
    UUID updateCustomer(@PathVariable("id") UUID id,
                        @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest);

    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable("id") UUID id);

    @PostMapping("/{id}/order")
    UUID createOrder(@PathVariable("id") UUID id);

    @GetMapping("/{id}/order")
     List<GetOrderResponse> getCustomersOrders (@PathVariable("id") UUID id);


    //TODO реализовать поиск
    //    @PostMapping("/search")
//    List<GetCustomerResponse> searchCustomers(SearchCustomerRequest searchCustomerRequest) throws IOException;
}
