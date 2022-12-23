package ru.ncs.DemoShop.controller.request.customerRequest;

import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCustomerRequest {
    @NotEmpty(message = "customer name should be not Empty")
    String name;

    @NotEmpty(message = "customer surname should be not Empty")
    String surname;

    @NotEmpty(message = "customer patronymic  should be not Empty")
    String patronymic;

    @Email
    @NotEmpty(message = "customer email should be not Empty")
    String email;

    @NotEmpty(message = "customer phone number should be not Empty")
    @Pattern(regexp = "^\\d+$")
    String phoneNumber;
}
