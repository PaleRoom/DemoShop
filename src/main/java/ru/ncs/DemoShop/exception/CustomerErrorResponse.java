package ru.ncs.DemoShop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
@AllArgsConstructor
public class CustomerErrorResponse {
    String message;
    String exceptionName;
    long timestamp;
}
