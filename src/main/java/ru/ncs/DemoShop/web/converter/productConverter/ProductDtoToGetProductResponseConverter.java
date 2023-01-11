package ru.ncs.DemoShop.web.converter.productConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.web.response.GetProductResponse;
import ru.ncs.DemoShop.service.product.data.ProductDTO;

@Component
public class ProductDtoToGetProductResponseConverter implements Converter<ProductDTO, GetProductResponse> {
    @Override
    public  GetProductResponse convert(ProductDTO productDTO) {
        return GetProductResponse.builder()
                .id(productDTO.getId())
                .amount(productDTO.getAmount())
                .category(productDTO.getCategory())
                .amountUpdatedAt(productDTO.getAmountUpdatedAt())
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .availability(productDTO.isAvailability())
                .build();
    }
}
