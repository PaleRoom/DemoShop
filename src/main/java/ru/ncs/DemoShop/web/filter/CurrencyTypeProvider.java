package ru.ncs.DemoShop.web.filter;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class CurrencyTypeProvider {
    private String currencyType;

    public CurrencyTypeProvider() {
        this.currencyType = "RUB";
    }
}
