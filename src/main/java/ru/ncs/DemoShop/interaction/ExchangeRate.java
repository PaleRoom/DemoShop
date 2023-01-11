package ru.ncs.DemoShop.interaction;

import java.util.HashMap;
import lombok.Data;
import ru.ncs.DemoShop.service.exchanging.CurrencyEnum;

@Data
public class ExchangeRate {
    private HashMap<CurrencyEnum,Double> exchangeRate;
}
