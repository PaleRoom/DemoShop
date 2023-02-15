package ru.ncs.DemoShop.service.exchanging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.interaction.ExchangeRate;
import ru.ncs.DemoShop.interaction.ExchangeTakingClient;
import ru.ncs.DemoShop.web.filter.CurrencyTypeProvider;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeTakingProvider {
    private final CurrencyTypeProvider currencyTypeProvider;
    private final ExchangeTakingClient exchangeTakingClient;


    public Double takeExchangeRate() {
        try {
            ExchangeRate exchangeRate = Optional.ofNullable(getRemoteRate()).or(() -> Optional.ofNullable(getLocalRate())).orElseThrow(RuntimeException::new);

            return exchangeRate.getExchangeRate().get(currencyTypeProvider.getCurrencyType());
        } catch (RuntimeException e) {
            return 1.0;
        }
    }

    private @Nullable ExchangeRate getRemoteRate() {
        log.info("getRemoteRate invoked");
        try {
            ExchangeRate rate = exchangeTakingClient.takeRate();
            log.debug("Exchange Service is running, Exchange rate has taken: {}", rate);

            return rate;
        } catch (Exception e) {
            log.info("Exchange service is unavailable - going to apply JSON");

            return null;
        }
    }

    private @Nullable ExchangeRate getLocalRate() {
        log.info("getLocalRate invoked");
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("ExchangeRate.json")) {
            File file = new File("tempExchangingCurrency.txt");
            FileUtils.copyInputStreamToFile(input, file);
            ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);
            log.debug("Exchange rate has taken from JSON: {}", exchangeRate);
            file.delete();

            return exchangeRate;
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
