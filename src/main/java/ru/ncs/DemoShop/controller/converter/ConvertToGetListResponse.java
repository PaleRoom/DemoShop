package ru.ncs.DemoShop.controller.converter;

import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetListResponse;
import ru.ncs.DemoShop.controller.response.GetProductResponse;

@Component
public class ConvertToGetListResponse implements Converter<List<GetProductResponse>, GetListResponse> {
    @Override
    public GetListResponse convert(List<GetProductResponse> source) {
        return GetListResponse.builder()
                .products(source)
                .build();
    }


}
