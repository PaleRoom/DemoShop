package ru.ncs.DemoShop.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ncs.DemoShop.models.Product;
import ru.ncs.DemoShop.repositories.ProductRepository;
import ru.ncs.DemoShop.util.ProductNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findOne(UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.orElseThrow(ProductNotFoundException::new);
    }

    public Product findOneForValidator(UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.orElse(null);
    }
//
//    public Product findOne(String name) {
//        Optional<Product> foundProduct = productRepository.findByName(name);
//        return foundProduct.orElseThrow(ProductNotFoundException::new);
//    }

    public Product findOneByName(String name) {
        Optional<Product> foundProduct = productRepository.findByName(name);
        return foundProduct.orElse(null);
    }

    @Transactional
    public void save(Product product) {
        product.setAmountUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    @Transactional
    public void update(Product product) {

        if (productRepository.findById(product.getId()).get().getAmount() != product.getAmount())
            product.setAmountUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }




}
