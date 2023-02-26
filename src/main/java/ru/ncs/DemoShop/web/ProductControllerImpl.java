package ru.ncs.DemoShop.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ncs.DemoShop.web.request.productRequest.CreateProductRequest;
import ru.ncs.DemoShop.web.request.productRequest.SearchProductRequest;
import ru.ncs.DemoShop.web.request.productRequest.UpdateProductRequest;
import ru.ncs.DemoShop.web.response.GetProductResponse;
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.service.product.ProductService;
import ru.ncs.DemoShop.service.product.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.product.immutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.product.immutable.ImmutableUpdateProductRequest;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ConversionService conversionService;

    @Override
    @GetMapping
    public List<GetProductResponse> getProducts() {
        return productService.findAll().stream()
                .map(dto -> conversionService.convert(dto, GetProductResponse.class))
                .collect(Collectors.toList());
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
    public List<GetProductResponse> searchProducts(@RequestBody SearchProductRequest searchProductRequest) {
        ImmutableSearchProductRequest immutableSearchProductRequest =
                conversionService.convert(searchProductRequest, ImmutableSearchProductRequest.class);

        return productService.searchProducts(immutableSearchProductRequest).stream()
                .map(dto -> conversionService.convert(dto, GetProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addFromCSV(@RequestParam("file") MultipartFile csvFile) {
        try {
            productService.readCSV(CreateProductRequest.class, csvFile.getInputStream()).stream()
                    .map(dto->conversionService.convert(dto, ImmutableCreateProductRequest.class))
                    .forEach(productService::save);
        } catch (IOException e) {
            e.printStackTrace();
        } ;
    }


}
