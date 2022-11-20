package ru.ncs.DemoShop.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.exception.ProductNotCreatedException;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.exception.ProductNotUpdatedException;
import ru.ncs.DemoShop.interfaces.ProductService;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final DatabaseConverterImpl databaseConvertServiceImpl;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(this.databaseConvertServiceImpl::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findOne(UUID id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        return databaseConvertServiceImpl.convertToProductDTO(foundProduct.orElseThrow(ProductNotFoundException::new));
    }

    @Override
    public ProductDTO findOneByName(String name) {
        Optional<Product> foundProduct = productRepository.findByName(name);
        return databaseConvertServiceImpl.convertToProductDTO(foundProduct.orElseThrow(ProductNotFoundException::new));
    }

    @Override
    @Transactional
    public UUID save(ImmutableCreateProductRequest request) {

        Product product = databaseConvertServiceImpl.convertToProduct(request);
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

    @Transactional
    public ProductDTO update(ImmutableUpdateProductRequest immutableUpdateProductRequest) {

        Product product = databaseConvertServiceImpl.convertToProduct(immutableUpdateProductRequest);
        try {
            UUID Pr1 = findOneByName(product.getName()).getId();
            UUID Pr2 = product.getId();
            if (!Pr1.equals(Pr2)) {
                throw new ProductNotUpdatedException("Product with this name is already exists");
            }
        } catch (ProductNotFoundException ignored) {
        }

        if (productRepository.findById(product.getId()).get().getAmount() != product.getAmount()) {
            product.setAmountUpdatedAt(LocalDateTime.now());
        }
        productRepository.save(product);
        return databaseConvertServiceImpl.convertToProductDTO(product);
    }

    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
