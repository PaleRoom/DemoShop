package ru.ncs.DemoShop.controller.exchanging;

public interface ExchangeTakingClient {
    /**
     * Метод, возвращающий обменный курс из удаленного сервиса
     * @return Double значение обменного курса
     */
    Double takeRate();
}
