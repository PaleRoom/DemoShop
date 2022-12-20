package ru.ncs.DemoShop.controller.exchanging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExchangeTakingClientImpl.class)
public class ExchangeTakingClientStub implements ExchangeTakingClient {
    private static final double MAX = 120.0;
    private static final double MIN = 1.0;
    @Override
    public Double takeRate() {

        return (Math.random() * ((MAX- MIN) + 1)) + MIN;
    }
}
