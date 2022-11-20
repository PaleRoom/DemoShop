package ru.ncs.DemoShop.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter @Setter @AllArgsConstructor @Value
public class ProductErrorResponse {
     String message;
     long timestamp;

}
