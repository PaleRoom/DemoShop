package ru.ncs.DemoShop.interaction;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.service.exchanging.CurrencyEnum;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeTakingClientImpl.class)
public class ExchangeTakingClientStub implements ExchangeTakingClient {
    private static final double MAX = 120.0;
    private static final double MIN = 1.0;

    @Override
    public ExchangeRate takeRate() {
        log.info("STUB method " + Thread.currentThread().getStackTrace()[0].getMethodName() + " of class" +
                this.getClass().getSimpleName() + " called");
        ExchangeRate rate = new ExchangeRate();
        HashMap<CurrencyEnum, Double> map = new HashMap<>() {{
            put(CurrencyEnum.USD, (Math.random() * ((MAX - MIN) + 1)) + MIN);
            put(CurrencyEnum.RUB, (Math.random() * ((MAX - MIN) + 1)) + MIN);
            put(CurrencyEnum.KZT, (Math.random() * ((MAX - MIN) + 1)) + MIN);
            put(CurrencyEnum.YEN, (Math.random() * ((MAX - MIN) + 1)) + MIN);
        }};
        rate.setExchangeRate(map);

        return rate;
    }
}
