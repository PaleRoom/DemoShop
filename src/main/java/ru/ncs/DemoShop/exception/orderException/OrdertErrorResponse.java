package ru.ncs.DemoShop.exception.orderException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
@AllArgsConstructor
public class OrdertErrorResponse {
     String message;
     String exceptionName;
     long timestamp;
}
