package ru.ncs.DemoShop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncs.DemoShop.models.Product;
import ru.ncs.DemoShop.services.ProductService;

@Component
public class ProductValidator implements Validator {

    private final ProductService productService;

    @Autowired
    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if
        (productService.findOneByName(product.getName()) != null) {
            errors.rejectValue("name", "", "Product with this name is already exists ");
        }
    }


}
