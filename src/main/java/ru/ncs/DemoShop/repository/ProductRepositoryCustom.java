package ru.ncs.DemoShop.repository;

import ru.ncs.DemoShop.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
     List<Product> searchProduct (String name, int amount, double price, boolean availability);
}
