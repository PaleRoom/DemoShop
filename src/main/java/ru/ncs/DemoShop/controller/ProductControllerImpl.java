package ru.ncs.DemoShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetListResponse;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.service.ProductService;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ConversionService conversionService;

    @Override
    @GetMapping
    public GetListResponse getProducts() {
        List<GetProductResponse> list = conversionService.convert(productService.findAll(), List.class);
        return conversionService.convert(list, GetListResponse.class);
    }

    @Override
    public GetProductResponse getProduct(@PathVariable("id") UUID id) {
        return conversionService.convert(productService.findOne(id), GetProductResponse.class);
    }

    @Override
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(@RequestBody @Valid CreateProductRequest createProductRequest) {
        ImmutableCreateProductRequest immutableCreateProductRequest = conversionService.convert(createProductRequest, ImmutableCreateProductRequest.class);
        return productService.save(immutableCreateProductRequest);
    }

    @Override
    public UUID updateProduct(@PathVariable("id") UUID id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        ImmutableUpdateProductRequest immutableUpdateProductRequest =
                conversionService.convert(updateProductRequest, ImmutableUpdateProductRequest.class);

        return productService.update(immutableUpdateProductRequest, id).getId();
    }

    @Override
    public void deleteProduct(@PathVariable("id") UUID id) {
        if (productService.findOne(id) == null) {
            throw new ProductNotFoundException();
        }
        productService.delete(id);

    }
}
