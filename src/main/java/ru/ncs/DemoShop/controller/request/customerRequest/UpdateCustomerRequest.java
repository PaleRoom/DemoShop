package ru.ncs.DemoShop.controller.request.customerRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UpdateCustomerRequest {
    @NotEmpty(message = "customer name should be not Empty")
    private String name;

    @NotEmpty(message = "customer surname should be not Empty")
    private String surname;

    @NotEmpty(message = "customer patronymic  should be not Empty")
    private String patronymic;

    @Email
    @NotEmpty(message = "customer email should be not Empty")
    private String email;

    @NotEmpty(message = "customer phone number should be not Empty")
    @Pattern( regexp = "^\\d+$")
    private String phoneNumber;
}
