package ru.ncs.DemoShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerConverterImpl implements ru.ncs.DemoShop.interfaces.ControllerConverter {

@Override
    public GetProductResponse convertToGetProductResponse(ProductDTO productDTO) {

        return GetProductResponse.builder()
                .id(productDTO.getId())
                .amount(productDTO.getAmount())
                .category(productDTO.getCategory())
                .amountUpdatedAt(productDTO.getAmountUpdatedAt())
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
    }
    @Override
    public ImmutableCreateProductRequest convertToImmutableCreateProductRequest(CreateProductRequest createProductRequest) {
        return ImmutableCreateProductRequest.builder()
                .amount(createProductRequest.getAmount())
                .category(createProductRequest.getCategory())
                .description(createProductRequest.getDescription())
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .build();
    }
    @Override
    public ImmutableUpdateProductRequest convertToImmutableUpdateProductRequest(UpdateProductRequest updateProductRequest, UUID id) {
        return ImmutableUpdateProductRequest.builder()
                .id(id)
                .amount(updateProductRequest.getAmount())
                .category(updateProductRequest.getCategory())
                .description(updateProductRequest.getDescription())
                .name(updateProductRequest.getName())
                .price(updateProductRequest.getPrice())
                .build();
    }
    @Override
    public List<GetProductResponse> convertToGetList(List<ProductDTO> listDTO) {
        return listDTO.stream().map(this::convertToGetProductResponse)
                .collect(Collectors.toList());
    }

}

