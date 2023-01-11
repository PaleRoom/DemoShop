package ru.ncs.DemoShop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.ncs.DemoShop.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    /**
     * Метод для поиска товара по названию
     *
     * @param name название искомого товара
     * @return Product возвращает результат найденный товар
     */
    Optional<Product> findByName(String name);

    /**
     * Метод для поиска ID товара по названию
     *
     * @param name название искомого товара
     * @return UUID возвращает ID найденного товара
     */
    @Query("select b.id from Product b where b.name = :name")
    Optional<UUID> findIdByName(String name);

    /**
     * Метод возвращающий список всех товаров из БД с применением Lock
     *
     * @return возвращает список всех товаров из БД
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT p FROM Product p ")
    List<Product> findAllLocked();
}
