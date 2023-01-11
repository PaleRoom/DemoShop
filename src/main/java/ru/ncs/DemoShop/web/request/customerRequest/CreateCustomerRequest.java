package ru.ncs.DemoShop.web.request.customerRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCustomerRequest {
    @NotBlank(message = "Поле name ну должно быть пустым")
    String name;

    @NotBlank(message = "Поле surname ну должно быть пустым")
    String surname;

    @NotBlank(message = "Поле  patronymic ну должно быть пустым")
    String patronymic;

    @NotBlank(message = "Поле email не должно быть пустым")
    @Pattern(regexp=".+@.+\\..+", message="Поле должно содержать валидный email")
    String email;

    @NotBlank(message = "Поле phone number не должно быть пустым")
    @Pattern(regexp = "^\\d+$")
    String phoneNumber;
}
