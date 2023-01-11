package ru.ncs.DemoShop.controller.exchanging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeTakingProvider {
    private final CurrencyTypeProvider currencyTypeProvider;
    private final ExchangeTakingClient exchangeTakingClient;


    public Double takeExchangeRate() {
        return Optional.ofNullable(getRemoteRate(currencyTypeProvider.getCurrencyType()))
                .or(() -> Optional.ofNullable(getLocalRate(currencyTypeProvider.getCurrencyType())))
                .orElse(1.0);
    }

    private @Nullable Double getRemoteRate(String currencyType) {
        log.info("getRemoteRate invoked");
        try {
            Double rate = exchangeTakingClient.takeRate(currencyType);
            log.debug("Exchange Service is running, Exchange rate has taken: {}", rate);

            return rate;
        } catch (Exception e) {
            log.info("Exchange service is unavailable - going to apply JSON");

            return null;
        }
    }

    private @Nullable Double getLocalRate(String currencyType) {
        log.info("getLocalRate invoked");
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            if (classLoader.getResource("ExchangeRate.json") != null) {
                File file = new File(classLoader.getResource("ExchangeRate.json").getFile());
                ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);
                Double rate = exchangeRate.getExchangeRate().get(currencyType);
                log.debug("Exchange rate has taken from JSON: {}", rate);

                return rate;
            } else throw new IOException("Local JSON file not found");
        } catch (IOException e) {
            log.info("Cannot take rate from local JSON");

            return null;
        }
    }

    @CacheEvict(value = "exchangeRates", allEntries = true)
    public void evictLocalRate(Double rate) {
        log.info("Evicting Rate {}", rate);
    }
}
