package ru.ncs.DemoShop.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCustomerResponse {
    UUID id;
    String name;
    String surname;
    String patronymic;
    String email;
    String phoneNumber;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    LocalDateTime updatedAt;
}
