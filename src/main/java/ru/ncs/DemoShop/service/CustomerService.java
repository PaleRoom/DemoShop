package ru.ncs.DemoShop.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.data.CustomerDTO;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableCreateCustomerRequest;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableUpdateCustomerRequest;

@Service
public interface CustomerService {
    CustomerDTO findOne(UUID id);
    CustomerDTO findOneByEmail(String email);
    UUID findIdByEmail(String email);
    List<CustomerDTO> findAll();
    UUID save(ImmutableCreateCustomerRequest immutableCreateCustomerRequest);
    CustomerDTO update(ImmutableUpdateCustomerRequest request, UUID id);
    void delete(UUID id);

    //TODO реализовать поиск заказчиков по критериям
    //List<CustomerDTO> searchCustomers(ImmutableSearchCustomerRequest request);
}
