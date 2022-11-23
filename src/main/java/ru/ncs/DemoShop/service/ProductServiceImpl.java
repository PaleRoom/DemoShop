package ru.ncs.DemoShop.service;


import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncs.DemoShop.exception.ProductNotCreatedException;
import ru.ncs.DemoShop.exception.ProductNotFoundException;
import ru.ncs.DemoShop.exception.ProductNotUpdatedException;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.repository.ProductRepository;
import ru.ncs.DemoShop.repository.specification.ProductSpecification;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableSearchProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ConversionService conversionService;
    //private final ProductSpecification productSpecification;

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

    @Override
    @Transactional
    public ProductDTO update(ImmutableUpdateProductRequest request, UUID id) {

        Product product = conversionService.convert(request, Product.class);
        product.setId(id);
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
        return conversionService.convert(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> searchProducts(ImmutableSearchProductRequest request)  {

        Specification<Product> specs = Specification.where(ProductSpecification.lessPrice(request.getPrice()))
                .and(ProductSpecification.likeName(request.getName()))
                .and(ProductSpecification.greaterThanOrEqualToAmount(request.getAmount()))
                .and(ProductSpecification.isAvailable(request.getAvailability()));

        List<Product> foundList = productRepository.findAll(specs);

        List<ProductDTO> listDTO = new ArrayList<>();
        for (Product product : foundList) {
            listDTO.add(conversionService.convert(product, ProductDTO.class));
        }
        return listDTO;
    }



}
