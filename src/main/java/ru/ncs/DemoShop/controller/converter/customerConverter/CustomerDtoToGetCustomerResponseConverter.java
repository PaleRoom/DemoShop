package ru.ncs.DemoShop.controller.converter.customerConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;
import ru.ncs.DemoShop.service.data.CustomerDTO;

@Component
public class CustomerDtoToGetCustomerResponseConverter implements Converter<CustomerDTO, GetCustomerResponse> {
    @Override
    public GetCustomerResponse convert(CustomerDTO source) {
        return GetCustomerResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .patronymic(source.getPatronymic())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .updatedAt(source.getUpdatedAt())
                .build();
    }
}
