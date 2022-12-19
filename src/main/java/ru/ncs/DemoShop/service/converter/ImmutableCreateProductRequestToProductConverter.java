package ru.ncs.DemoShop.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;

@Component
public class ImmutableCreateProductRequestToProductConverter implements Converter<ImmutableCreateProductRequest, Product> {
    @Override
    public Product convert(ImmutableCreateProductRequest source) {
        Product product = new Product();
        product.setName(source.getName());
        product.setAmount(source.getAmount());
        product.setCategory(source.getCategory());
        product.setPrice(source.getPrice());
        product.setDescription(source.getDescription());
        product.setAvailability(source.isAvailability());

        return product;
    }
}
