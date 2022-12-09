package ru.ncs.DemoShop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetListResponse;
import ru.ncs.DemoShop.controller.response.GetProductResponse;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/default")
@Tag(name = "Product service", description = "the Product API with description tag annotation")
public interface ProductController {

    @GetMapping
    GetListResponse getProducts();

    @GetMapping("/{id}")
    GetProductResponse getOneProduct(@PathVariable("id") UUID id);

    @PostMapping
    UUID create(@RequestBody @Valid CreateProductRequest createProductRequest);

    @PutMapping("/{id}")
    UUID updateProduct(@PathVariable("id") UUID id,
                             @RequestBody @Valid UpdateProductRequest updateProductRequest);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") UUID id);

}
