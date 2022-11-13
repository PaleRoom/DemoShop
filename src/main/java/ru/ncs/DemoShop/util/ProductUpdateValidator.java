package ru.ncs.DemoShop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncs.DemoShop.models.Product;
import ru.ncs.DemoShop.services.ProductService;

@Component
public class ProductUpdateValidator implements Validator {


    private final ProductService productService;

    @Autowired
    public ProductUpdateValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if (productService.findOneForValidator(product.getId()) == null)
            errors.rejectValue("name", "", "Product with this name is not exists ");
        else if (productService.findOneByName(product.getName()).getId() != product.getId())
            errors.rejectValue("name", "", "Product with this name is already exists ");
    }


}


