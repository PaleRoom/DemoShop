package ru.ncs.DemoShop.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.service.customer.data.CustomerDTO;

@Component
public class CustomerToCustomerDTOConverter implements Converter<Customer, CustomerDTO> {
    @Override
    public CustomerDTO convert(Customer source) {
        return CustomerDTO.builder()
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
