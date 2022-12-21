package ru.ncs.DemoShop.controller.exchanging;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.ncs.DemoShop.exception.ExchangeInputException;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.exchange.remoteEnabled", matchIfMissing = true)
public class ExchangeTakingClientImpl implements ExchangeTakingClient {
    @Value("${app.exchange.URL}")
    String url;

    @Cacheable(cacheNames = "exchangeRates")
    public Double takeRate() {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRate resp = restTemplate.getForObject(url, ExchangeRate.class);
        log.debug("Rate Entity taken from URL: {}", resp);

        return Optional.ofNullable(resp)
                .map(r -> resp.getUsdExchangeRate())
                .orElseThrow(() -> new ExchangeInputException("Json from Service not valid"));
    }
}
