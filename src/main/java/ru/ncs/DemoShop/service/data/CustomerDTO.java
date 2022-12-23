package ru.ncs.DemoShop.service.data;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerDTO {
    UUID id;
    String name;
    String surname;
    String patronymic;
    String email;
    String phoneNumber;
    LocalDateTime updatedAt;
}
