package ru.ncs.DemoShop.repository;

public interface RedisRepository {
    /**
     * Метод добавления ключа идемпотентности
     *
     * @param idpKey  хэш-ключ
     * @param orderId значение хэша
     * @return возвращает значение хэша после создания
     */
    String addIdpKey(String idpKey, String orderId);

    /**
     * метод для получения значения хэша из таблицы идемпотентности по ключу
     *
     * @param id хэш-ключ
     * @return возвращает значение хэша
     */
    String getIdpValue(String id);
}
