package ru.ncs.DemoShop.controller.advice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ru.ncs.DemoShop.controller.exchanging.ExchangeTakingProvider;
import ru.ncs.DemoShop.controller.response.GetProductResponse;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ListGetProductAdvice implements ResponseBodyAdvice<List<GetProductResponse>> {
    private final ExchangeTakingProvider exchangeTakingProvider;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String className = returnType.getContainingClass().toString();
        String methodName = returnType.getMethod().toString();

        return className.contains("ProductControllerImpl") && methodName.contains("Products");
    }

    @Override
    public List<GetProductResponse> beforeBodyWrite(@Nullable List<GetProductResponse> body, MethodParameter returnType,
                                                    MediaType selectedContentType,
                                                    Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                                    ServerHttpRequest request, ServerHttpResponse response) {

        Double rate = exchangeTakingProvider.takeExchangeRate("USD");
        Optional.ofNullable(body).ifPresent(b -> b.forEach(p -> p.setPrice(p.getPrice() / rate)));

        return body;
    }
}
