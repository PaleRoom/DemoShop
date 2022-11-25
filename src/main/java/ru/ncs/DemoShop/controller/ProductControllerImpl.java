package ru.ncs.DemoShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.SearchProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetListResponse;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.service.ProductServiceImpl;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductServiceImpl productServiceImpl;
    private final ConversionService conversionService;
    private final SearchedToXlsSaver searchedToXlsSaver;
    private final SearchedToPDFsaver searchedToPDFsaver;

    @Override
    @GetMapping
    public GetListResponse getProducts() {
        List<ProductDTO> ListDTO = productServiceImpl.findAll();
        List<GetProductResponse> listGPR = new ArrayList<>();
        for (ProductDTO product : ListDTO) {
            listGPR.add(conversionService.convert(product, GetProductResponse.class));
        }
        GetListResponse responseList = conversionService.convert(listGPR, GetListResponse.class);
        return responseList;
    }

    @Override
    public GetProductResponse getOneProduct(@PathVariable("id") UUID id) {
        return conversionService.convert(productServiceImpl.findOne(id), GetProductResponse.class);
    }

    @Override
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(@RequestBody @Valid CreateProductRequest createProductRequest) {
        ImmutableCreateProductRequest immutableCreateProductRequest = conversionService.convert(createProductRequest, ImmutableCreateProductRequest.class);
        return productServiceImpl.save(immutableCreateProductRequest);
    }

    @Override
    public UUID updateProduct(@PathVariable("id") UUID id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        ImmutableUpdateProductRequest immutableUpdateProductRequest =
                conversionService.convert(updateProductRequest, ImmutableUpdateProductRequest.class);

        return productServiceImpl.update(immutableUpdateProductRequest, id).getId();
    }

    @Override
    public void deleteProduct(@PathVariable("id") UUID id) {
        if (productServiceImpl.findOne(id) == null) {
            throw new ProductNotFoundException();
        }
        productServiceImpl.delete(id);
    }

    @Override
    public GetListResponse searchProducts(@RequestBody SearchProductRequest searchProductRequest) throws IOException {
        System.out.println(searchProductRequest);
        ImmutableSearchProductRequest immutableSearchProductRequest =
                conversionService.convert(searchProductRequest, ImmutableSearchProductRequest.class);
        List<ProductDTO> ListDTO = productServiceImpl.searchProducts(immutableSearchProductRequest);

        List<GetProductResponse> listGPR = new ArrayList<>();
        for (ProductDTO product : ListDTO) {
            listGPR.add(conversionService.convert(product, GetProductResponse.class));
        }

        GetListResponse responseList = conversionService.convert(listGPR, GetListResponse.class);
        searchedToPDFsaver.saveSearchedToPdf(responseList);
        searchedToXlsSaver.SaveSearchedToXls(responseList);
        return responseList;
    }
}
