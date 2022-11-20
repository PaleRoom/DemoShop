package ru.ncs.DemoShop.service;

import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.interfaces.DatabaseConverter;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;

@Component
public class DatabaseConverterImpl implements DatabaseConverter {
    @Override
    public ProductDTO convertToProductDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .amount(product.getAmount())
                .category(product.getCategory())
                .amountUpdatedAt(product.getAmountUpdatedAt())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    @Override
    public Product convertToProduct(ImmutableCreateProductRequest immutableCreateProductRequest) {
        Product product = new Product();
        product.setName(immutableCreateProductRequest.getName());
        product.setAmount(immutableCreateProductRequest.getAmount());
        product.setCategory(immutableCreateProductRequest.getCategory());
        product.setPrice(immutableCreateProductRequest.getPrice());
        product.setDescription(immutableCreateProductRequest.getDescription());
        return product;
    }

    @Override
    public Product convertToProduct(ImmutableUpdateProductRequest immutableUpdateProductRequest) {
        Product product = new Product();
        product.setId(immutableUpdateProductRequest.getId());
        product.setName(immutableUpdateProductRequest.getName());
        product.setAmount(immutableUpdateProductRequest.getAmount());
        product.setCategory(immutableUpdateProductRequest.getCategory());
        product.setPrice(immutableUpdateProductRequest.getPrice());
        product.setDescription(immutableUpdateProductRequest.getDescription());
        return product;
    }

}

