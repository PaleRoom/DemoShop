package ru.ncs.DemoShop.service;

import lombok.Data;

@Data
public class Idempotent {
    private String idpKey;
    private String orderID;
}
