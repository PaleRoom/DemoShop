package ru.ncs.DemoShop.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncs.DemoShop.web.request.orderRequest.UpdateOrderRequest;
import ru.ncs.DemoShop.web.response.GetFullOrderResponse;
import ru.ncs.DemoShop.web.response.GetOrderResponse;

/**
 * Контроллер для выполнения операций над заказами
 */
@RequestMapping("/orders")
@Tag(name = "Order service", description = "the Order API with description tag annotation")
public interface OrderController {
    /**
     * Метод, создающий новый пустой заказ у заказчика
     *
     * @param id - ID заказчика, для которого будет создан новый заказ
     * @return UUID - возвращает ID созданного заказа
     */
    @PostMapping("/customer/{id}")
    UUID createOrder(@PathVariable("id") UUID id);

    /**
     * Метод, возвращающий все заказы из БД
     *
     * @return список заказов
     */
    @GetMapping
    List<GetOrderResponse> getOrders();

    /**
     * Метод, возвращающий заказ по его ID
     *
     * @param id - ID искомого заказа
     * @return GetFullOrderResponse возвращает сущность-ответ, содержащую параметры заказа и список с содержимым заказа
     * в упрощенном виде:
     * - наименование товара
     * - количество товара
     * - цена товара
     */
    @GetMapping("/{id}")
    GetFullOrderResponse getOneOrder(@PathVariable("id") UUID id);

    /**
     * Метод, возвращающий все заказы конкретного заказчика
     *
     * @param id - ID заказчика, заказы которого нужно получить
     * @return возвращает список заказов заказчика
     */
    @GetMapping("/customer/{id}")
    List<GetOrderResponse> getCustomersOrders(@PathVariable("id") UUID id);

    /**
     * Метод для обновления данных заказа. Принимает список товаров, добавляемых в заказ, а также их количество.
     * возможен ввод отрицательного количества товара для коррекции заказа
     *
     * @param id                 - ID обновляемого заказа
     * @param updateOrderRequest список запросов на добавление товара в заказ
     * @return UUID возвращает ID обновленного заказа
     */
    @PutMapping("/{id}")
    UUID updateOrder(@PathVariable("id") UUID id,
                     @RequestBody @Valid List<UpdateOrderRequest> updateOrderRequest);

    /**
     * Метод, удаляющий заказ
     *
     * @param id - ID удаляемого заказа
     */
    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable("id") UUID id);

    /**
     * Метод закрывающий заказ. Значение поле STATUS меняется на CLOSED
     *
     * @param id - ID закрываемого заказа
     */
    @PatchMapping("/{id}")
    void closeOrder(@PathVariable("id") UUID id);

    /**
     * Метод, отменяющий заказ:
     * - Значение поле STATUS меняется на CANCELED
     * - количество товаров на складе увеличивается на величины количества соответствующих товаров
     * бывших в заказе до отмены (возвращаются на склад)
     *
     * @param id - ID отменяемого заказа
     */
    @PatchMapping("/{id}/cancel")
    void cancelOrder(@PathVariable("id") UUID id);

}
