package ru.ncs.DemoShop.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.SearchProductRequest;
import ru.ncs.DemoShop.controller.request.UpdateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

@Component
public class ConvertToImmutableSearchProductRequest implements Converter<SearchProductRequest, ImmutableSearchProductRequest> {
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
