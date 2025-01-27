package com.fripop.product.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Data transfer object used for product creation.
 *
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto implements Serializable {

    /**
     * Unique alphanumeric identifier.
     */
    @Length(max = 255, message = "Product code length cannot exceed 255 characters")
    @Schema(description = "Unique alphanumeric identifier")
    private String code;

    /**
     * {@link Boolean} flag indicating if product is active or not.
     */
    @Schema(description = "Flag indicating if product is active", defaultValue = "true")
    private Boolean active = true;

    /**
     * Product name.
     */
    @NotBlank(message = "Product name must be provided.")
    @Length(max = 255, message = "Product name length cannot exceed 255 characters")
    @Schema(description = "Name")
    private String name;

    /**
     * Product price.
     */
    @NotNull(message = "Product price must be provided.")
    @Positive(message = "Product price must be positive.")
    @Schema(description = "Price", example = "22.5")
    private BigDecimal price;
}
