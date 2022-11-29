package ru.ncs.DemoShop.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.exception.ProductNotCreatedException;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.exception.ProductNotUpdatedException;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
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
    @Transactional(readOnly = true)
    public ProductDTO findOne(UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return conversionService.convert(foundProduct.orElseThrow(ProductNotFoundException::new), ProductDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findOneEnt(UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.orElseThrow(ProductNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findOneByName(String name) {
        Optional<Product> foundProduct = productRepository.findByName(name);
        return conversionService.convert(foundProduct.orElseThrow(ProductNotFoundException::new), ProductDTO.class);
    }

    @Version
    @Transactional(readOnly = true)
    public UUID findIdByName(String name) {
        return productRepository.findIdByName(name).orElseThrow(ProductNotFoundException::new);
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
        log.info("Product saved");
        return product.getId();
    }

    @Override
    @Transactional
    public ProductDTO update(ImmutableUpdateProductRequest request, UUID id) {

        Product product = findOneEnt(id);
        if (request.getName() != null) {
            try {
                if (!(findIdByName(request.getName()).equals(id))) {
                    throw new ProductNotUpdatedException("Product with this name is already exists");
                }
            } catch (ProductNotFoundException ignored) {
            }
            product.setName(request.getName());
        }

        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getAmount() != null) product.setAmount(request.getAmount());
        if (request.getDescription() != null) product.setDescription(request.getDescription());

        productRepository.save(product);
        log.info("Product updated");
        return conversionService.convert(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void increasePrice(double mod) throws InterruptedException {
        List<Product> sourceList = productRepository.findAll();
        for (Product product : sourceList) {
            product.setPrice(product.getPrice() * mod);
        }
        log.debug("Sleep for {}s due to a Lock-test with modificator {}", 30, mod);//for Lock-testing purposes
        Thread.sleep(30000);
        productRepository.saveAll(sourceList);
        log.info("Price increased");
    }
}
