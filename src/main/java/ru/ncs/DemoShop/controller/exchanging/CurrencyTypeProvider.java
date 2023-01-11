package ru.ncs.DemoShop.controller.exchanging;

import lombok.Data;

@Data
public class CurrencyTypeProvider {
    private String currencyType;

    public CurrencyTypeProvider() {
        this.currencyType = "RUB";
    }
}
