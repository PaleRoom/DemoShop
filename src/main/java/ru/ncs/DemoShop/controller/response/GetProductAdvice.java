package ru.ncs.DemoShop.controller.response;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.ncs.DemoShop.controller.exchanging.ExchangeTakingProvider;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GetProductAdvice implements ResponseBodyAdvice<GetProductResponse> {
    private ExchangeTakingProvider exchangeTakingProvider;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String className = returnType.getContainingClass().toString();
        String methodName = returnType.getMethod().toString();
        return className.contains("ProductControllerImpl") && methodName.contains("getOneProduct");
    }

    @Override
    public GetProductResponse beforeBodyWrite(GetProductResponse body, MethodParameter returnType,
                                              MediaType selectedContentType,
                                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                              ServerHttpRequest request, ServerHttpResponse response) {


        double rate = exchangeTakingProvider.takeExchangeRate();
        return GetProductResponse.builder()
                .name(body.getName())
                .availability(body.isAvailability())
                .description(body.getDescription())
                .amount(body.getAmount())
                .amountUpdatedAt(body.getAmountUpdatedAt())
                .category(body.getCategory())
                .id(body.getId())
                .price(body.getPrice() / rate)
                .build();
    }

}
