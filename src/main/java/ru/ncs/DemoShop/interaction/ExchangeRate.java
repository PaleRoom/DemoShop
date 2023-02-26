package ru.ncs.DemoShop.interaction;

import java.util.HashMap;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.ncs.DemoShop.service.exchanging.CurrencyEnum;

@Data
@Jacksonized
public class ExchangeRate {
    private HashMap<CurrencyEnum,Double> exchangeRate;
}
