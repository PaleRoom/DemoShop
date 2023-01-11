package ru.ncs.DemoShop.exception;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ErrorDetails {
    String message;
    String exceptionName;
    long timestamp;
}
