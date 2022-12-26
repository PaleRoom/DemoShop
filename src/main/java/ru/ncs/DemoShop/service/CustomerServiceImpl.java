package ru.ncs.DemoShop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.ncs.DemoShop.exception.customerException.CustomerNotCreatedException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotFoundException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotUniqueException;
import ru.ncs.DemoShop.exception.productException.ProductNotFoundException;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.repository.CustomerRepository;
import ru.ncs.DemoShop.service.data.CustomerDTO;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableCreateCustomerRequest;
import ru.ncs.DemoShop.service.immutable.customerImmutable.ImmutableUpdateCustomerRequest;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findOne(UUID id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        return conversionService.convert(foundCustomer.orElseThrow(() ->
                new CustomerNotFoundException("Customer with id not found: "
                        + id.toString())), CustomerDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findOneByEmail(String email) {
        Assert.hasText(email, "name must not be blank");
        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);

        return conversionService.convert(foundCustomer.orElseThrow(CustomerNotFoundException::new), CustomerDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UUID findIdByEmail(String email) {
        return customerRepository.findIdByEmail(email).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> list = customerRepository.findAll();
        List<CustomerDTO> listDTO = new ArrayList<>();
        for (Customer customer : list) {
            listDTO.add(conversionService.convert(customer, CustomerDTO.class));
        }

        return listDTO;
    }

    @Override
    @Transactional
    public UUID save(ImmutableCreateCustomerRequest request) {
        Customer customer = conversionService.convert(request, Customer.class);
        try {
            if (findOneByEmail(customer.getEmail()) != null) {
                throw new CustomerNotCreatedException("This Customer is already exists!");
            }
        } catch (CustomerNotFoundException ignored) {
        }
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        log.info("Product saved");

        return customer.getId();
    }

    private boolean checkUnique(final String email, final UUID id) {
        final boolean check = customerRepository.findIdByEmail(email).map(entId ->
                Objects.equals(entId, id)).orElse(true);
        if (!check) {
            throw new CustomerNotUniqueException("Email must be unique");
        }

        return true;
    }

    @Override
    @Transactional
    public CustomerDTO update(ImmutableUpdateCustomerRequest request, UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id not found: " + id.toString()));
        if (request.getEmail() != null && checkUnique(request.getEmail(), id)) {
            customer.setEmail(request.getEmail());
        }
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setPatronymic(request.getPatronymic());
        customer.setPhoneNumber(request.getPhoneNumber());

        customerRepository.save(customer);
        log.debug("Customer updated, ID: {}", id);

        return conversionService.convert(customer, CustomerDTO.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }
}
