package ru.ncs.DemoShop.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.service.ProductService;
@Slf4j
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
        log.info("Performing price increase scheduling with 15s delay");
        productService.increasePrice(mod);

    }
}
