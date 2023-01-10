package ru.ncs.DemoShop.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ncs.DemoShop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    /**
     * Метод для поиска заказчика по email
     *
     * @param email email искомого заказчика
     * @return Сгыещьук возвращает найденного заказчика
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Метод поиска ID заказчика по email
     *
     * @param email email искомого заказчика
     * @return UUID возвращает ID найденного заказчика
     */
    @Query("select b.id from Customer b where b.email = :email")
    Optional<UUID> findIdByEmail(String email);
}
