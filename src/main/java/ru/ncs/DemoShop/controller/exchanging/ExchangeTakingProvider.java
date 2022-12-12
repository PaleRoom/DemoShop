package ru.ncs.DemoShop.controller.exchanging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import ru.ncs.DemoShop.exception.ExchangeInputException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeTakingProvider {

    private final ExchangeTakingClient exchangeTakingClient;
    private CacheManager cacheManager;


    public Double takeExchangeRate() {

        Double rate = null;
        log.info("Take exchangeRate invoked");
        try {
            rate = exchangeTakingClient.takeRateFromURL();

            log.debug("Exchange Service is running, Exchange rate has taken: {}", rate);
        } catch (HttpServerErrorException.ServiceUnavailable
                 | HttpServerErrorException.GatewayTimeout
                 | HttpServerErrorException.InternalServerError
                 | ExchangeInputException e) {
            log.info("That service unavailable - going to apply JSON");

        }
        if (rate == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("ExchangeRate.json").getFile());

            try {
                ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);
                rate = exchangeRate.getExchangeRate();
                log.debug("Exchange rate has taken from JSON: {}", rate);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return rate;
    }

    @CacheEvict(value = "exchangeRates", allEntries = true)
    public void evictLocalRate(Double rate) {
        log.info("Evicting Rate {}", rate);
    }

}
