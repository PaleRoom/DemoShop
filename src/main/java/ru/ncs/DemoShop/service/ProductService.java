package ru.ncs.DemoShop.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

@Service
public interface ProductService {
    ProductDTO findOne(UUID id);

    ProductDTO findOneByName(String name);

    UUID findIdByName(String name);

    List<ProductDTO> findAll();

    @Transactional
     UUID save(ImmutableCreateProductRequest immutableCreateProductRequest);

    @Transactional
     ProductDTO update(ImmutableUpdateProductRequest request, UUID id);

    @Transactional
     void delete(UUID id);
    @Transactional
    void increasePrice(double mod) throws InterruptedException;
}
