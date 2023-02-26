package ru.ncs.DemoShop.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.interaction.ExchangeRate;

@Service
@Profile("local")
@RequiredArgsConstructor
@Slf4j

public class Consumer {
    private ExchangeRate rate;

    @KafkaListener(topics = "ExchangeTopic3", containerFactory = "kafkaListenerContainerFactoryMessage")
    public void listenGroupTopic2(ConsumerRecord<String, ExchangeRate> record) {
        log.info("Receive message {}", record);
    }

    @KafkaListener(topics = "ExchangeTopic2", containerFactory = "kafkaListenerContainerFactoryString")
    public void listenGroupTopic1(String message) throws JsonProcessingException {
        log.info("Receive message {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        rate = objectMapper.readValue(message, ExchangeRate.class);
        log.info("Receive RATE {}", rate);
        // producer.sendMessage("ExchangeTopic2", 23, 24, exchangeResponse.takeRate(),2 );

    }

    public ExchangeRate getListenersRate() {
        return rate;
    }
}
