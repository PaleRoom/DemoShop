package ru.ncs.DemoShop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncs.DemoShop.controller.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;
import ru.ncs.DemoShop.controller.response.GetOrderResponse;

@RequestMapping("/default")
@Tag(name = "Orderr service", description = "the Order API with description tag annotation")
public interface OrderController {
    @GetMapping
    List<GetOrderResponse> getOrders();

    @GetMapping("/{id}")
    GetCustomerResponse getOneOrder(@PathVariable("id") UUID id);

//    @PostMapping
//    UUID create(@RequestBody @Valid CreateCustomerRequest createCustomerRequest);

    @PutMapping("/{id}")
    UUID updateOrder(@PathVariable("id") UUID id,
                        @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest);

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable("id") UUID id);

}
