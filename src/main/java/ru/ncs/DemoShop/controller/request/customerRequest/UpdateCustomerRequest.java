package ru.ncs.DemoShop.controller.request.customerRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateCustomerRequest {
    @NotBlank(message = "Поле name ну должно быть пустым")
    String name;

    @NotBlank(message = "Поле surname ну должно быть пустым")
    String surname;

    @NotBlank(message = "Поле  patronymic ну должно быть пустым")
    String patronymic;

    @Email(message = "Поле должно содержать email")
    @NotBlank(message = "Поле email не должно быть пустым")
    String email;

    @NotBlank(message = "Поле phone number не должно быть пустым")
    @Pattern(regexp = "^\\d+$")
    String phoneNumber;
}
