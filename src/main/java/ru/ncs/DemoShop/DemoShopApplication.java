package ru.ncs.DemoShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoShopApplication.class, args);

    }
}
