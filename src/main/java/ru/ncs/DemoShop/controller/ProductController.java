package ru.ncs.DemoShop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.SearchProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetListResponse;
import ru.ncs.DemoShop.controller.response.GetProductResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/default")
@Tag(name = "Product service", description = "the Product API with description tag annotation")
public interface ProductController {

    @GetMapping
    GetListResponse getProducts();

    @GetMapping("/{id}")
    GetProductResponse getProduct(@PathVariable("id") UUID id);

    @PostMapping
    UUID create(@RequestBody @Valid CreateProductRequest createProductRequest);

    @PutMapping("/{id}")
    UUID updateProduct(@PathVariable("id") UUID id,
                             @RequestBody @Valid UpdateProductRequest updateProductRequest);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") UUID id);

    @PostMapping("/search")
    GetListResponse searchProducts(SearchProductRequest searchProductRequest);
}
