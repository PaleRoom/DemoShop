package ru.ncs.DemoShop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.ncs.DemoShop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);

    @Query("select b.id from Customer b where b.email = :email")
    Optional<UUID> findIdByEmail(String email);

//    @Override
//    @Lock(LockModeType.PESSIMISTIC_READ)
//    List<Customer> findAll();
}
