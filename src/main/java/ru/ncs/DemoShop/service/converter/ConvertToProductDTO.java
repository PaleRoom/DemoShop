package ru.ncs.DemoShop.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.service.data.ProductDTO;

@Component
public class ConvertToProductDTO implements Converter<Product, ProductDTO> {
    @Override
    public ProductDTO convert(Product source) {
        return ProductDTO.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .category(source.getCategory())
                .amountUpdatedAt(source.getAmountUpdatedAt())
                .description(source.getDescription())
                .name(source.getName())
                .price(source.getPrice())
                .availability(source.isAvailability())
                .build();
    }
}
