package ru.ncs.DemoShop.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncs.DemoShop.model.Order;
import ru.ncs.DemoShop.model.OrderedId;
import ru.ncs.DemoShop.model.OrderedProduct;
import ru.ncs.DemoShop.model.Product;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedId> {
    List<OrderedProduct> findOrderedProductByOrder(Order order);
    List<OrderedProduct> findOrderedProductByProduct(Product product);
    OrderedProduct findByOrderIdAndProductId(UUID order_id, UUID prod_id);
}
