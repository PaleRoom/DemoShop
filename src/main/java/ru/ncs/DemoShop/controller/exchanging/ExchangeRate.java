package ru.ncs.DemoShop.controller.exchanging;

import java.util.HashMap;
import lombok.Data;

@Data
public class ExchangeRate {
    private HashMap<String,Double> exchangeRate;
}
