package com.fripop.product.ws.repository;

import com.fripop.product.ws.exception.NotFoundException;
import com.fripop.product.ws.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    default Page<Product> findAll(String name, BigDecimal amountStart, BigDecimal amountEnd, Boolean active, Pageable pageable) {
        return this.findAll((root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            if (name != null) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }

            if (amountStart != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("amount"), amountStart));
            }

            if (amountEnd != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("amount"), amountEnd));
            }

            if (active != null) {
                predicates.add(builder.equal(root.get("active"), active));
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        }, pageable);
    }

    default Product findRequiredById(long id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException("Cannot find Product with id " + id));
    }
}
