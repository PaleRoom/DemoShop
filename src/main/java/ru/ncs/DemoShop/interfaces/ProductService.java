package ru.ncs.DemoShop.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public interface ProductService {

    ProductDTO findOne(UUID id);
    ProductDTO findOneByName(String name);

    List<ProductDTO> findAll();

    @Transactional
     UUID save(ImmutableCreateProductRequest immutableCreateProductRequest);




}
