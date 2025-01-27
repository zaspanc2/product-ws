package com.fripop.product.ws.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Product entity model.
 * <p>
 * Represents 'product' table in database.
 *
 * @since 1.0.0
 */
@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}),
        @UniqueConstraint(columnNames = {"name"})
})
@Getter
@Setter
public class Product {

    /**
     * Auto generating primary numeric identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Alphanumeric identifier.
     */
    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    /**
     * {@link Timestamp} of creation.
     */
    @Column(name = "created", updatable = false)
    private Timestamp created;

    /**
     * {@link Timestamp} of last update.
     */
    @Column(name = "updated")
    private Timestamp updated;

    /**
     * {@link Boolean} flag indicating if product is active or not.
     */
    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Product name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Product price.
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * Method that is executed before persist.
     */
    @PrePersist
    public void onCreate() {
        if (this.created == null) {
            this.created = new Timestamp(System.currentTimeMillis());
        }

        // Auto generate code if it is empty.
        if (StringUtils.isEmpty(this.code)) {
            this.code = UUID.randomUUID().toString();
        }
    }

    /**
     * Method that is executed before update.
     */
    @PreUpdate
    public void onUpdate() {
        this.updated = new Timestamp(System.currentTimeMillis());
    }
}
