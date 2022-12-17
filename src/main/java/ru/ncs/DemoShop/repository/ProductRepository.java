package ru.ncs.DemoShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.ncs.DemoShop.model.Product;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);

    @Query("select b.id from Product b where b.name = :name")
    Optional<UUID> findIdByName(String name);

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Product> findAll();
}
