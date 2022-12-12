package ru.ncs.DemoShop.controller.exchanging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeTakingClientImpl.class)
public class ExchangeTakingClientStub implements ExchangeTakingClient {
    @Override
    public Double takeRateFromURL() {
        double max = 120.0;
        double min = 1.0;
        return (Math.random() * ((max - min) + 1)) + min;
    }
}
