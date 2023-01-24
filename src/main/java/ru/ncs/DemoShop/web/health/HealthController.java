package ru.ncs.DemoShop.web.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @Value("${app.healthCheckMsg}")
    private String msg;

    @GetMapping("/healthcheck")
    public HealthCheck healthCheck() {
        System.out.println("request");
        return HealthCheck.builder()
                .status(HealthCheck.Status.OK)
                .msg(this.msg)
                .build();
    }
}
