package ru.ncs.DemoShop.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.productImutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.productImutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.immutable.productImutable.ImmutableUpdateProductRequest;

@Service
public interface ProductService {
    ProductDTO findOne(UUID id);

    ProductDTO findOneByName(String name);

    UUID findIdByName(String name);

    List<ProductDTO> findAll();

    UUID save(ImmutableCreateProductRequest immutableCreateProductRequest);

    ProductDTO update(ImmutableUpdateProductRequest request, UUID id);

    void delete(UUID id);

    void increasePrice(double mod) throws InterruptedException;

    List<ProductDTO> searchProducts(ImmutableSearchProductRequest request);
}
