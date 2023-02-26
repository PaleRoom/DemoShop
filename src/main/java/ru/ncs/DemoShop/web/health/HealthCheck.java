package ru.ncs.DemoShop.web.health;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;

@Data
@Builder
@Profile("local")
public class HealthCheck {

    private Status status;

    private String msg;

    public enum Status {
        OK,
        FAIL
    }
}
