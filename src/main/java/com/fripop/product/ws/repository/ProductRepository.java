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

/**
 * JPA repository for {@link Product} entity.
 *
 * @since 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Finds all {@link Product}s by parameters.
     *
     * @param name       optional part or full name of a product
     * @param priceStart optional product price start
     * @param priceEnd   optional product price end
     * @param active     optional flag for including active/inactive products
     * @param pageable   {@link Pageable} with pagination information
     * @return {@link Page} with {@link Product}s
     */
    default Page<Product> findAll(String name, BigDecimal priceStart, BigDecimal priceEnd, Boolean active, Pageable pageable) {
        return this.findAll((root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            // Name filter.
            if (name != null) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }

            // Price start filter.
            if (priceStart != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), priceStart));
            }

            // Price end filter.
            if (priceEnd != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), priceEnd));
            }

            // Active filter.
            if (active != null) {
                predicates.add(builder.equal(root.get("active"), active));
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        }, pageable);
    }

    /**
     * Finds {@link Product} by id.
     * <p>
     * Throws {@link NotFoundException} if product does not exist.
     *
     * @param id product id
     * @return {@link Product}
     * @throws NotFoundException when product does not exist
     */
    default Product findRequiredById(long id) throws NotFoundException {
        return this.findById(id).orElseThrow(() -> new NotFoundException("Cannot find Product with id " + id));
    }
}
