package ru.ncs.DemoShop.producer;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.context.annotation.Profile;

@Value
@Builder
@Jacksonized
@Profile("local")
public class Message {
    String text;
    int value;
}
