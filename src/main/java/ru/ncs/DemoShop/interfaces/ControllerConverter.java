package ru.ncs.DemoShop.interfaces;

import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import java.util.List;
import java.util.UUID;


public interface ControllerConverter {
    GetProductResponse convertToGetProductResponse(ProductDTO productDTO);

    ImmutableCreateProductRequest convertToImmutableCreateProductRequest(CreateProductRequest createProductRequest);

    ImmutableUpdateProductRequest convertToImmutableUpdateProductRequest(UpdateProductRequest updateProductRequest, UUID id);

    List<GetProductResponse> convertToGetList(List<ProductDTO> listDTO);
}
