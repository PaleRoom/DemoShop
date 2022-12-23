package ru.ncs.DemoShop.controller.converter.customerConverter;

import org.springframework.core.convert.converter.Converter;
import ru.ncs.DemoShop.controller.request.customerRequest.CreateCustomerRequest;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableCreateCustomerRequest;

public class CreateCustomerRequestToImmutableCreateCustomerRequestConverter implements Converter<CreateCustomerRequest, ImmutableCreateCustomerRequest> {
    @Override
    public ImmutableCreateCustomerRequest convert(CreateCustomerRequest source) {
        return ImmutableCreateCustomerRequest.builder()
                .name(source.getName())
                .surname(source.getSurname())
                .patronymic(source.getPatronymic())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}
