package ru.ncs.DemoShop.interaction;

public interface ExchangeTakingClient {
    /**
     * Метод, возвращающий обменный курс из удаленного сервиса
     * @return Double значение обменного курса
     */
    ExchangeRate takeRate();
}
