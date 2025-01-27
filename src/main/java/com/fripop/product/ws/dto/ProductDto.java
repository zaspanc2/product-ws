package com.fripop.product.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Product data transfer object.
 *
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {

    /**
     * Unique primary identifier.
     */
    @Schema(description = "Unique numeric identifier")
    private Long id;

    /**
     * Unique alphanumeric identifier.
     */
    @Schema(description = "Unique alphanumeric identifier")
    private String code;

    /**
     * {@link Timestamp} of creation.
     */
    @Schema(description = "Timestamp of when record was created")
    private Timestamp created;

    /**
     * {@link Timestamp} of last update.
     */
    @Schema(description = "Timestamp of when record was updated")
    private Timestamp updated;

    /**
     * {@link Boolean} flag indicating if product is active or not.
     */
    @Schema(description = "Flag indicating if product is active")
    private Boolean active;

    /**
     * Product name.
     */
    @Schema(description = "Name")
    private String name;

    /**
     * Product price.
     */
    @Schema(description = "Price", example = "22.5")
    private BigDecimal price;
}
