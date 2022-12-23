package ru.ncs.DemoShop.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableCreateCustomerRequest;

@Component
public class ImmutableCreateCustomerRequestToCustomerConverter implements Converter<ImmutableCreateCustomerRequest, Customer> {
    public Customer convert(ImmutableCreateCustomerRequest source) {
        Customer customer = new Customer();
        customer.setName(source.getName());
        customer.setSurname(source.getSurname());
        customer.setPatronymic(source.getPatronymic());
        customer.setEmail(source.getEmail());
        customer.setPhoneNumber(source.getPhoneNumber());

        return customer;
    }
}