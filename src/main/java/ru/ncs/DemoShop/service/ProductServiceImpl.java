package ru.ncs.DemoShop.service;


import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.exception.ProductNotCreatedException;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.exception.ProductNotUniqueException;
import ru.ncs.DemoShop.exception.ProductNotUpdatedException;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ConversionService conversionService;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> list = productRepository.findAll();
        List<ProductDTO> listDTO = new ArrayList<>();
        for (Product product : list) {
            listDTO.add(conversionService.convert(product, ProductDTO.class));
        }
        return listDTO;
    }

    @Override
    public ProductDTO findOne(UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return conversionService.convert(foundProduct.orElseThrow(ProductNotFoundException::new), ProductDTO.class);
    }

    @Override
    public ProductDTO findOneByName(String name) {
        Optional<Product> foundProduct = productRepository.findByName(name);
        return conversionService.convert(foundProduct.orElseThrow(ProductNotFoundException::new), ProductDTO.class);
    }

    @Override
    @Transactional
    public UUID save(ImmutableCreateProductRequest request) {
        Product product = conversionService.convert(request, Product.class);
        try {
            if (findOneByName(product.getName()) != null) {
                throw new ProductNotCreatedException("This Product is already exists!");
            }
        } catch (ProductNotFoundException ignored) {
        }
        product.setAmountUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return product.getId();
    }


    private boolean checkUnique(final String name, final UUID id) {
        final boolean check = productRepository.findIdByName(name).map(entId -> Objects.equals(entId, id)).orElse(true);
        if (!check) {
            throw new ProductNotUniqueException("Name must be unique");
        }
        return true;
    }

    @Override
    @Transactional
    public ProductDTO update(ImmutableUpdateProductRequest request, UUID id) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        if (request.getName() != null && checkUnique(request.getName(), id)) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getAmount() != null) product.setAmount(request.getAmount());
        if (request.getDescription() != null) product.setDescription(request.getDescription());

        productRepository.save(product);
        log.debug("Product updated, ID: {}", id);
        return conversionService.convert(product, ProductDTO.class);
    }



    @Override
    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
