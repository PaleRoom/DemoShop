package ru.ncs.DemoShop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceIncreaseScheduler {
    private ProductServiceImpl productServiceImpl;
    @Value("${app.scheduling.priceModificator}")
    private double mod;

    public PriceIncreaseScheduler(ProductServiceImpl productServiceImpl, @Value("${app.scheduling.priceModificator}") double mod) {
        this.productServiceImpl = productServiceImpl;
        this.mod = mod;
    }

    @Scheduled(fixedDelayString = "${app.scheduling.period}", initialDelay = 10000)
    public void increasePrices() throws InterruptedException {
        productServiceImpl.increasePrice(mod);
    }
}
