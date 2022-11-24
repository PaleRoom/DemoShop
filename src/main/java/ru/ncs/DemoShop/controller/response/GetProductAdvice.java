package ru.ncs.DemoShop.controller.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.File;
import java.io.IOException;

@RestControllerAdvice
public class GetProductAdvice implements ResponseBodyAdvice<GetProductResponse> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String className = returnType.getContainingClass().toString();

        String methodName = returnType.getMethod().toString();

        if (className.contains("ProductControllerImpl") && methodName.contains("getOneProduct")) {

            return true;
        }

        return false;
    }

    @Override
    public GetProductResponse beforeBodyWrite(GetProductResponse body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File ("src/main/resources/ExchangeRate.json");
       double rate;
        try {
            ExchangeRate exchangeRate = objectMapper.readValue(file,ExchangeRate.class);
            rate = exchangeRate.getExchangeRate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return GetProductResponse.builder()
                .name(body.getName()+" - ну это просто волшебство!")
                .availability(body.isAvailability())
                .description(body.getDescription())
                .amount(body.getAmount())
                .amountUpdatedAt(body.getAmountUpdatedAt())
                .category(body.getCategory())
                .id(body.getId())
                .price(body.getPrice()*rate)
                .build();
    }
}
