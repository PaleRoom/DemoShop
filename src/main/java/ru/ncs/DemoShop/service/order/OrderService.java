package ru.ncs.DemoShop.service.order;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.order.data.OrderDTO;
import ru.ncs.DemoShop.service.order.immutable.ImmutableUpdateOrderRequest;

@Service
public interface OrderService {
    /**
     * Метод для поиска заказа по ID
     * @param id ID, передаваемый для поиска заказа
     * @return  OrderDTO найденный заказ, возвращаемый методом
     */
    OrderDTO findOne(UUID id);

    /**
     * Метод для выдачи всех заказов из БД
     * @return возвращает список заказов из БД
     */
    List<OrderDTO> findAll();

    /**
     * Метод для выдачи всех заказов заказчика из БД
     * @param id - ID заказчика
     * @return возвращает список заказов заданного заказчика
     */
    List<OrderDTO> findCustomersOrders(UUID id);

    /**
     * Метод создающий новый пустой заказ для заданного заказчика
     * @param customerId - ID заказчика, для которого создается новый заказ
     * @return UUID - возвращает ID созданного заказа
     */
    UUID save(UUID customerId);

    /**
     * Метод для обновления данных заказа в БД
     * @param request параметр с сущностью-запросом для обновления данных заказа
     * @param orderId - ID обновляемого заказа
     * @return UUID - ID обновленного заказа
     */
    UUID update(List<ImmutableUpdateOrderRequest> request, UUID orderId);

    /**
     * Метод для удаления заказа из БД
     * @param id - ID удаляемого заказа
     */
    void delete(UUID id);

    /**
     * Метод закрывающий заказ. Значение поле STATUS меняется на CLOSED
     * @param id - ID закрываемого заказа
     */
    void closeOrder(UUID id);

    /**
     * Метод, отменяющий заказ:
     * - Значение поле STATUS меняется на CANCELED
     * - количество товаров на складе увеличивается на величины количества соответствующих товаров
     * бывших в заказе до отмены (возвращаются на склад)
     * @param id - ID отменяемого заказа
     */
    void cancelOrder(UUID id);
}
