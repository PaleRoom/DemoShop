package ru.ncs.DemoShop.exception.productException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
@AllArgsConstructor
public class ProductErrorResponse {
     String message;
     String exceptionName;
     long timestamp;
}
