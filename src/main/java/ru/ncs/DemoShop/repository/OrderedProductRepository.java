package ru.ncs.DemoShop.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncs.DemoShop.model.Order;
import ru.ncs.DemoShop.model.OrderedId;
import ru.ncs.DemoShop.model.OrderedProduct;
import ru.ncs.DemoShop.model.Product;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedId> {
    /**
     * Метод для получения  заказанных товаров из таблицы заказанных товаров по заданному заказу
     *
     * @param order параметр содержащий заказ, товары из которого нужно получить
     * @return возвращает список заказанных товаров
     */
    List<OrderedProduct> findOrderedProductByOrder(Order order);

    /**
     * Метод для получения заказанных товаров из таблицы заказанных товаров по заданному продукту
     *
     * @param product параметр содержащий тип товара, который нужно получить
     * @return возвращает список заказанных товаров
     */
    List<OrderedProduct> findOrderedProductByProduct(Product product);

    /**
     * Метод для получения заказанного товара из таблицы заказанных товаров по ID товара и ID заказа
     *
     * @param order_id - ID заказа
     * @param prod_id  - ID товара
     * @return OrderedProduct возвращает товар и его количесво
     */
    OrderedProduct findByOrderIdAndProductId(UUID order_id, UUID prod_id);
}
