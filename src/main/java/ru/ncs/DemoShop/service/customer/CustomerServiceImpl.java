package ru.ncs.DemoShop.service.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.exception.customerException.CustomerEmailAlreadyExistsException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotCreatedException;
import ru.ncs.DemoShop.exception.customerException.CustomerNotFoundException;
import ru.ncs.DemoShop.model.Customer;
import ru.ncs.DemoShop.repository.CustomerRepository;
import ru.ncs.DemoShop.service.customer.data.CustomerDTO;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableCreateCustomerRequest;
import ru.ncs.DemoShop.service.customer.immutable.ImmutableUpdateCustomerRequest;
import ru.ncs.DemoShop.service.order.data.OrderDTO;

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
    public UUID findIdByEmail(String email) {
        return customerRepository.findIdByEmail(email).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> list = customerRepository.findAll();

        return list.stream()
                .map(c -> conversionService.convert(c, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UUID save(ImmutableCreateCustomerRequest request) {
        Customer customer = conversionService.convert(request, Customer.class);
        customerRepository.findByEmail(customer.getEmail())
                .ifPresent(c -> {
                    throw new CustomerNotCreatedException("This Customer is already exists!");
                });
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        log.info("Product saved");

        return customer.getId();
    }

    private boolean checkUnique(final String email, final UUID id) {
        final boolean check = customerRepository.findIdByEmail(email).map(entId ->
                Objects.equals(entId, id)).orElse(true);
        if (!check) {
            throw new CustomerEmailAlreadyExistsException("Email must be unique");
        }

        return true;
    }

    @Override
    @Transactional
    public CustomerDTO update(ImmutableUpdateCustomerRequest request, UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer with Id not found: " + id.toString()));
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
