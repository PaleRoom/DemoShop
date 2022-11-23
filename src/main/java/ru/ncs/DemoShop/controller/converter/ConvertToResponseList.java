package ru.ncs.DemoShop.controller.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetProductResponse;
import ru.ncs.DemoShop.service.data.ProductDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
final class ConvertToResponseList implements Converter<List<ProductDTO>, List<GetProductResponse>> {
    private final ConversionService conversionService;

    @Override
    public List<GetProductResponse> convert(List<ProductDTO> source) {
        List<GetProductResponse> list = new ArrayList<>();
        for (ProductDTO product : source) {
            list.add(conversionService.convert(product, GetProductResponse.class));
        }
        return list;
    }
}