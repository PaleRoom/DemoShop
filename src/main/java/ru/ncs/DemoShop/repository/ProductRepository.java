package ru.ncs.DemoShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ncs.DemoShop.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);

    List<Product> searchProduct (String name, int amount, double price, boolean availability);

}
