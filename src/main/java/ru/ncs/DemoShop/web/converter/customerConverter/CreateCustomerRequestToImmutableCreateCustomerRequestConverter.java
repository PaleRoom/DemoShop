package ru.ncs.DemoShop.web.converter.customerConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.web.request.customerRequest.CreateCustomerRequest;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableCreateCustomerRequest;

@Component
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
