package ru.ncs.DemoShop.service.immutable.customerImmutable;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImmutableCreateCustomerRequest {
    String name;
    String surname;
    String patronymic;
    String email;
    String phoneNumber;
}
