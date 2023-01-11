package ru.ncs.DemoShop.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    /**
     * Метод для поиска заказов по заказчику
     *
     * @param owner параметр, содержащий сущность заказчика
     * @return возвращает список заказов заказчика
     */
    List<Order> findByOwner(Customer owner);

    /**
     * Метод для поиска заказов по ID заказчика
     *
     * @param id - ID заказчика
     * @return возвращает список заказов заказчика с данным ID
     */
    List<Order> findByOwnerId(UUID id);

}
