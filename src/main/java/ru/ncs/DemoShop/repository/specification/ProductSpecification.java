package ru.ncs.DemoShop.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.model.Product_;

public class ProductSpecification {

    public static Specification<Product> likeName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
    }
    public static Specification<Product> greaterThanOrEqualToAmount(Integer amount) {
        if (amount==null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.AMOUNT), amount );
    }

    public static Specification<Product> lessPrice(Double price) {
        if (price==null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Product_.PRICE), price );
    }
    public static Specification<Product> isAvailable(Boolean availability){
        if (availability==null) {
            return null;
        }
        if (availability)  {return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Product_.AVAILABILITY));}
    else {return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get(Product_.AVAILABILITY));}
    }
}
