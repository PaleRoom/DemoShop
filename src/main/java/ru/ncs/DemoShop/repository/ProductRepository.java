package ru.ncs.DemoShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncs.DemoShop.model.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);

}
