package ru.ncs.DemoShop.producer;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Message {
    String text;
    int value;
}
