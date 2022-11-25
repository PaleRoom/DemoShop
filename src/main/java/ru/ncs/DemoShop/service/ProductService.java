package ru.ncs.DemoShop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public interface ProductService {
    ProductDTO findOne(UUID id);

    ProductDTO findOneByName(String name);

    List<ProductDTO> findAll();

    @Transactional
     UUID save(ImmutableCreateProductRequest immutableCreateProductRequest);
    @Transactional
     ProductDTO update(ImmutableUpdateProductRequest request, UUID id);
    @Transactional
     void delete(UUID id);
}
