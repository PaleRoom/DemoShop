package ru.ncs.DemoShop.interaction;

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
    public ExchangeRate takeRate() {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRate resp = restTemplate.getForObject(url, ExchangeRate.class);
        log.debug("Rate Entity taken from URL: {}", resp);
        log.debug("////////Rate taken from URL: {}", resp.getExchangeRate());

        return Optional.of(resp)
                .orElseThrow(() -> new ExchangeInputException("Json from Service not valid"));
    }
}
