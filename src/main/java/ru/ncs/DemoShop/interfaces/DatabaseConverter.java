package ru.ncs.DemoShop.interfaces;

import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.service.data.ProductDTO;
import ru.ncs.DemoShop.service.immutable.ImmutableCreateProductRequest;
import ru.ncs.DemoShop.service.immutable.ImmutableUpdateProductRequest;


public interface DatabaseConverter {

    ProductDTO convertToProductDTO(Product product);
    Product convertToProduct (ImmutableCreateProductRequest immutableCreateProductRequest);
    Product convertToProduct (ImmutableUpdateProductRequest immutableUpdateProductRequest);

}