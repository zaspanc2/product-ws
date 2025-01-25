package com.fripop.product.ws.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "created", updatable = false)
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @PrePersist
    public void onCreate() {
        if (this.created == null) {
            this.created = new Timestamp(System.currentTimeMillis());
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updated = new Timestamp(System.currentTimeMillis());
    }
}
