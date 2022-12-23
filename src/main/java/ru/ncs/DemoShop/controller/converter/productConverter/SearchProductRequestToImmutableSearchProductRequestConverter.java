package ru.ncs.DemoShop.controller.converter.productConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.productRequest.SearchProductRequest;
import ru.ncs.DemoShop.service.immutable.productImutable.ImmutableSearchProductRequest;

@Component
public class SearchProductRequestToImmutableSearchProductRequestConverter implements Converter<SearchProductRequest, ImmutableSearchProductRequest> {
    @Override
    public ImmutableSearchProductRequest convert(SearchProductRequest source) {
        return ImmutableSearchProductRequest.builder()
                .amount(source.getAmount())
                .name(source.getName())
                .price(source.getPrice())
                .availability(source.getAvailability())
                .build();
    }
}
