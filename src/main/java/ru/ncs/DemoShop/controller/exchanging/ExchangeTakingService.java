package ru.ncs.DemoShop.controller.exchanging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@CacheConfig(cacheNames = "exchangeRates")
@RequiredArgsConstructor
public class ExchangeTakingService {
    private Double rate;
    private boolean taken;

    @Cacheable
    public Double takeExchangeRate(String url) {
        log.info("Take exchangeRate invoked");

        try {
            RestTemplate restTemplate = new RestTemplate();
            ExchangeRate resp = restTemplate.getForObject(url, ExchangeRate.class);
            if (resp != null) {
                rate = resp.getExchangeRate();
            }
            taken = true;
            log.debug("Exchange Service is running, Exchange rate has taken: {}", rate);
        } catch (HttpServerErrorException.ServiceUnavailable
                 | HttpServerErrorException.GatewayTimeout
                 | HttpServerErrorException.InternalServerError e) {
            log.info("That service unavailable - going to apply JSON");

        }
        if (rate == null) {
            taken = false;
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/ExchangeRate.json");

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

    public boolean getTaken() {
        return taken;
    }
}
