package ru.ncs.DemoShop.controller.converter.customerConverter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableUpdateCustomerRequest;

@Component
public class UpdateCustomerRequestToImmutableUpdateCustomerRequestConverter implements Converter<UpdateCustomerRequest, ImmutableUpdateCustomerRequest> {
    @Override
    public ImmutableUpdateCustomerRequest convert(UpdateCustomerRequest source) {
        return ImmutableUpdateCustomerRequest.builder()
                .name(source.getName())
                .surname(source.getSurname())
                .patronymic(source.getPatronymic())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}
