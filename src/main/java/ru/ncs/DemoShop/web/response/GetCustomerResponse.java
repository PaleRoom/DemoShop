package ru.ncs.DemoShop.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCustomerResponse {
    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String phoneNumber;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy/MMMM/d HH:mm:ss")
    private LocalDateTime updatedAt;
}
