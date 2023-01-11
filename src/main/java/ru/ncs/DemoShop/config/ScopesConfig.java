package ru.ncs.DemoShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import ru.ncs.DemoShop.controller.exchanging.CurrencyTypeProvider;

@Configuration
public class ScopesConfig {
    @Bean
    @SessionScope
    public CurrencyTypeProvider sessionScopedBean() {
        return new CurrencyTypeProvider();
    }
}
