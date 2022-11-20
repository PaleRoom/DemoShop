package ru.ncs.DemoShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.service.ProductServiceImpl;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor

public class ProductControllerImpl implements ru.ncs.DemoShop.interfaces.ProductController {
    private final ProductServiceImpl productServiceImpl;
    private final ControllerConverterImpl controllerConverterImpl;

    @Override
    @GetMapping
    public List<GetProductResponse> getProducts() {
        return controllerConverterImpl.convertToGetList(productServiceImpl.findAll());
    }

    @Override
    public GetProductResponse getProduct(@PathVariable("id") UUID id) {
        return controllerConverterImpl.convertToGetProductResponse(productServiceImpl.findOne(id));
    }

    @Override
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(@RequestBody @Valid CreateProductRequest createProductRequest) {
        ImmutableCreateProductRequest immutableCreateProductRequest = controllerConverterImpl.convertToImmutableCreateProductRequest(createProductRequest);
        return productServiceImpl.save(immutableCreateProductRequest);
    }

    @Override
    public UUID updateProduct(@PathVariable("id") UUID id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        ImmutableUpdateProductRequest immutableUpdateProductRequest = controllerConverterImpl.convertToImmutableUpdateProductRequest(updateProductRequest, id);
        return productServiceImpl.update(immutableUpdateProductRequest).getId();
    }

    @Override
    public void deleteProduct(@PathVariable("id") UUID id) {
        if (productServiceImpl.findOne(id) == null) {
            throw new ProductNotFoundException();
        }
        productServiceImpl.delete(id);
    }
}
