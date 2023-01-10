package ru.ncs.DemoShop.service.customer;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.customer.data.CustomerDTO;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableCreateCustomerRequest;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableUpdateCustomerRequest;

@Service
public interface CustomerService {
    /**
     * Метод для поиска заказчика по ID
     * @param id ID, передаваемый для поиска заказчика
     * @return  CustomerDTO найденный заказчик, возвращаемый методом
     */
    CustomerDTO findOne(UUID id);

    /**
     * Метод для поиска ID заказчика по Email
     * @param email - email заказчика, ID которого следует вернуть
     * @return UUID - ID найденного заказчика, возвращаемый методом
     */
    UUID findIdByEmail(String email);

    /**
     * Метод для выдачи всех заказчиков из БД
     * @return возвращает список заказчиков из БД
     */
    List<CustomerDTO> findAll();

    /**
     * Метод для сохранения в БД нового заказчика
     * @param immutableCreateCustomerRequest параметр с сущностью заказчика для передачи в метод
     * @return UUID - возращает ID созданного заказчика
     */
    UUID save(ImmutableCreateCustomerRequest immutableCreateCustomerRequest);

    /**
     * Метод для обновления данных товара в БД
     * @param request параметр с сущностью-запросом для обновления данных заказчика
     * @param id ID обновляемого заказчика
     * @return возращает обновленного заказчика
     */
    CustomerDTO update(ImmutableUpdateCustomerRequest request, UUID id);

    /**
     * Метод, удаляющий заказчика БД
     * @param id - ID удаляемого заказчика
     */
    void delete(UUID id);
}
