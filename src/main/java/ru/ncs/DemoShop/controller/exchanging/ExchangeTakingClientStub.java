package ru.ncs.DemoShop.controller.exchanging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeTakingClientImpl.class)
public class ExchangeTakingClientStub implements ExchangeTakingClient {
    private static final double MAX = 120.0;
    private static final double MIN = 1.0;

    @Override
    public Double takeRate(String currencyType) {
        log.info("STUB method " + Thread.currentThread().getStackTrace()[0].getMethodName() + " of class" +
                this.getClass().getSimpleName() + " called");

        return (Math.random() * ((MAX - MIN) + 1)) + MIN;
    }
}
