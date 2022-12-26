package ru.ncs.DemoShop.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    List<Order> findByOwner(Customer owner);
    List<Order> findByOwnerId(UUID id);

}
