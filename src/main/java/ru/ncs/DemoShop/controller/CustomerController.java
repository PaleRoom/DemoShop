package ru.ncs.DemoShop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncs.DemoShop.controller.request.customerRequest.CreateCustomerRequest;
import ru.ncs.DemoShop.controller.request.customerRequest.UpdateCustomerRequest;
import ru.ncs.DemoShop.controller.response.GetCustomerResponse;

/**
 * Контроллер для выполнения операций над заказчиками
 */
@RequestMapping("/customers")
@Tag(name = "Customer service", description = "the Customer API with description tag annotation")
public interface CustomerController {
    /**
     * Метод, возвращающий всех заказчиков из БД
     * @return список заказчиков
     */
    @GetMapping
    List<GetCustomerResponse> getCustomers();

    /**
     * Метод, возвращающий одного заказчика
     * @param id ID возвращаемого заказчика
     * @return GetCustomerResponse возвращает сущность-ответ содержащую заказчика
     */
    @GetMapping("/{id}")
    GetCustomerResponse getOneCustomer(@PathVariable("id") UUID id);

    /**
     * Метод, создающий нового заказчика
     * @param createCustomerRequest параметр содержащий сущность-запрос с данными полей создаваемого заказчика
     * @return UUID - возвращает ID созданного заказчика
     */
    @PostMapping
    UUID create(@RequestBody @Valid CreateCustomerRequest createCustomerRequest);

    /**
     * Метод для обновления данных заказчика
     * @param id - ID обновляемого заказчика
     * @param updateCustomerRequest параметр, содержащий сущность-запрос с данными полей для обновления заказчика
     * @return UUID - возвращает ID обновленного заказчика
     */
    @PutMapping("/{id}")
    UUID updateCustomer(@PathVariable("id") UUID id,
                        @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest);

    /**
     * метод, удаляющий заказчика
     * @param id - ID удаляемого заказчика
     */
    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable("id") UUID id);
}
