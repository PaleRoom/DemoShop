package ru.ncs.DemoShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.scheduling.enabled", matchIfMissing = true)
public class PriceIncreaseScheduler {
    private final ProductService productService;
    @Value("${app.scheduling.priceModificator:1.0}")
    private double mod;

    @Scheduled(fixedDelayString = "${app.scheduling.period}", initialDelay = 10000)
    public void increasePrices() throws InterruptedException {
        productService.increasePrice(mod);
    }
}
