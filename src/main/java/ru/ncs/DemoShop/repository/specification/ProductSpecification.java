package ru.ncs.DemoShop.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.ncs.DemoShop.model.Product;
import ru.ncs.DemoShop.model.Product_;

public class ProductSpecification {

    public static Specification<Product> likeName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
        };
    }

    public static Specification<Product> greaterThanOrEqualToAmount(Integer amount) {

        return (root, query, criteriaBuilder) -> {
            if (amount == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.AMOUNT), amount);
        };
    }

    public static Specification<Product> lessPrice(Double price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(Product_.PRICE), price);
        };
    }

    public static Specification<Product> isAvailable(Boolean availability) {
        return (root, query, criteriaBuilder) -> {
            if (availability == null)
                return criteriaBuilder.conjunction();
            if (availability) {
                return criteriaBuilder.isTrue(root.get(Product_.AVAILABILITY));
            } else
                return criteriaBuilder.isFalse(root.get(Product_.AVAILABILITY));
        };
    }
}
