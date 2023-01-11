package ru.ncs.DemoShop.web.filter;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.ncs.DemoShop.service.exchanging.CurrencyEnum;

@Data
@Component
@SessionScope
public class CurrencyTypeProvider {
    private CurrencyEnum currencyType;

    public CurrencyTypeProvider() {
        this.currencyType = CurrencyEnum.RUB;
    }
}
