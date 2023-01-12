package ru.ncs.DemoShop.repository;

import ru.ncs.DemoShop.service.Idempotent;

public interface RedisRepository {
    void addIdpKey(String idpKey, String orderId);
    void deleteIdpKey(String id);
    String getIdpKey(String id);
}
