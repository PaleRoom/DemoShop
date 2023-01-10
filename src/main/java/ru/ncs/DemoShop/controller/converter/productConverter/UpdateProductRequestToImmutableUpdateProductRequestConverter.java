package ru.ncs.DemoShop.controller.converter.productConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.productRequest.UpdateProductRequest;
import ru.ncs.DemoShop.service.product.immutable.ImmutableUpdateProductRequest;

@Component
public class UpdateProductRequestToImmutableUpdateProductRequestConverter implements Converter<UpdateProductRequest, ImmutableUpdateProductRequest> {
    @Override
    public ImmutableUpdateProductRequest convert(UpdateProductRequest source) {
        return ImmutableUpdateProductRequest.builder()
                .amount(source.getAmount())
                .category(source.getCategory())
                .description(source.getDescription())
                .name(source.getName())
                .price(source.getPrice())
                .availability(source.getAvailability())
                .build();
    }
}
