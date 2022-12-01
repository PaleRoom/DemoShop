package ru.ncs.DemoShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ncs.DemoShop.model.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);

    @Query("select b.id from Product b where b.name = :name")
    Optional<UUID> findIdByName(String name);

}
