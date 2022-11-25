package ru.ncs.DemoShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import ru.ncs.DemoShop.model.Product;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String name);

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Product> findAll();
}
