package ru.ncs.DemoShop.service.idempotence;

import lombok.Data;

@Data
public class Idempotent {
    private String idpKey;
    private String orderID;
}
