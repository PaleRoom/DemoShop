package ru.ncs.DemoShop.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.CreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;

@Component
public class ConvertToImmutableCreateProductRequest implements Converter<CreateProductRequest, ImmutableCreateProductRequest> {
    @Override
    public ImmutableCreateProductRequest convert(CreateProductRequest source) {
        return ImmutableCreateProductRequest.builder()
                .amount(source.getAmount())
                .category(source.getCategory())
                .description(source.getDescription())
                .name(source.getName())
                .price(source.getPrice())
                .availability(source.isAvailability())
                .build();
    }
}
