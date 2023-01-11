package ru.ncs.DemoShop.service.customer.immutable;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImmutableUpdateCustomerRequest {
    String name;
    String surname;
    String patronymic;
    String email;
    String phoneNumber;
}
