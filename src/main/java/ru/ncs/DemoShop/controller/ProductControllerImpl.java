package ru.ncs.DemoShop.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.SearchProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.service.ProductService;
import ru.ncs.DemoShop.service.SearchResultSaver;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ConversionService conversionService;
    private final SearchResultSaver searchResultSaver;

    @Override
    @GetMapping
    public List<GetProductResponse> getProducts() {
        List <GetProductResponse>list =
                productService.findAll().stream().map(dto ->
                        conversionService.convert(dto, GetProductResponse.class)).collect(Collectors.toList());
        return list;
    }

    @Override
    public GetProductResponse getOneProduct(@PathVariable("id") UUID id) {
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

    @Override
    public List<GetProductResponse> searchProducts(@RequestBody SearchProductRequest searchProductRequest) throws IOException {
         ImmutableSearchProductRequest immutableSearchProductRequest =
                conversionService.convert(searchProductRequest, ImmutableSearchProductRequest.class);
        List <GetProductResponse>list = productService.searchProducts(immutableSearchProductRequest).stream().map(dto ->
                conversionService.convert(dto, GetProductResponse.class)).collect(Collectors.toList());
//         searchResultSaver.saveSearchedToPdf(list);
//        searchResultSaver.SaveSearchedToXls(list);
        return productService.searchProducts(immutableSearchProductRequest).stream().map(dto ->
                conversionService.convert(dto, GetProductResponse.class)).collect(Collectors.toList());
    }
}
