package ru.ncs.DemoShop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(name = "app.scheduling.enabled", matchIfMissing = true)
public class PriceIncreaseScheduler {
    private final ProductService productService;
    private final double mod;

    public PriceIncreaseScheduler(ProductServiceImpl productService, @Value("${app.scheduling.priceModificator:1.0}") double mod) {
        this.productService = productService;
        this.mod = mod;
    }

    @Scheduled(fixedDelayString = "${app.scheduling.period}", initialDelay = 10000)
    public void increasePrices() throws InterruptedException {
        productService.increasePrice(mod);
    }
}
