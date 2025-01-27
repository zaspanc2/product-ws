package com.fripop.product.ws.controller;

import com.fripop.product.ws.dto.ObjectsResponseDto;
import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductDto;
import com.fripop.product.ws.dto.ProductUpdateDto;
import com.fripop.product.ws.exception.NotFoundException;
import com.fripop.product.ws.service.ProductService;
import com.fripop.product.ws.util.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * {@link ProductController} handles product related requests.
 *
 * @since 1.0.0
 */
@Tag(name = "Product")
@Controller
@RequestMapping("/products")
@AllArgsConstructor
@Validated
public class ProductController {

    private final Logger logger = Logger.getLogger(ProductController.class);
    private final ProductService productService;

    /**
     * Handles requests for creating a new product.
     *
     * @param productCreate {@link ProductCreateDto} with creation information
     * @return created {@link ProductDto}
     */
    @RequestMapping(method = RequestMethod.POST)
    @Operation(operationId = "createProduct", summary = "Create a new product", description = "Handles requests for creating a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<ProductDto> create(@Parameter(description = "Product to be created", required = true) @Valid @NotNull @RequestBody ProductCreateDto productCreate) {

        // Create a new product.
        var createdProduct = productService.create(productCreate);
        logger.info("Created product with id: " + createdProduct.getId());

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Handles requests for updating an existing product.
     *
     * @param id            existing product id
     * @param productUpdate {@link ProductUpdateDto} with update information
     * @return {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT}
     * @throws NotFoundException when product does not exist
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @Operation(operationId = "updateProduct", summary = "Update an existing product", description = "Handles requests for updating an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<String> update(@Parameter(description = "Product id", required = true, example = "1") @PathVariable long id,
                                         @Parameter(description = "Product to be updated", required = true) @Valid @NotNull @RequestBody ProductUpdateDto productUpdate)
            throws NotFoundException {

        // Update an existing product.
        productService.update(id, productUpdate);
        logger.info("Updated product with id: " + id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles requests for reading a product by id.
     *
     * @param id existing product id
     * @return {@link ProductDto}
     * @throws NotFoundException when product does not exist
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @Operation(operationId = "findProduct", summary = "Find product by id", description = "Handles requests for reading an existing product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<ProductDto> find(@Parameter(description = "Product id", required = true, example = "1") @NotNull @Positive @PathVariable("id") long id)
            throws NotFoundException {

        // Read an existing product.
        var product = productService.findRequired(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Handles requests for reading all products by parameters.
     *
     * @param name       optional part or full name of a product
     * @param priceStart optional product price start
     * @param priceEnd   optional product price end
     * @param active     optional flag for including active/inactive products
     * @param pageable   {@link Pageable} with pagination information
     * @return {@link ObjectsResponseDto} containing a collection of {@link ProductDto}s
     */
    @RequestMapping(method = RequestMethod.GET)
    @Operation(operationId = "findAllProducts", summary = "Find all products by parameters", description = "Handles requests for reading all existing products by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<ObjectsResponseDto<ProductDto>> findAll(@Parameter(description = "Optional part or full product name", example = "book") @RequestParam(name = "name", required = false) String name,
                                                                  @Parameter(description = "Optional product price start value", example = "11.3") @RequestParam(name = "price-start", required = false) BigDecimal priceStart,
                                                                  @Parameter(description = "Optional product price end value", example = "22.5") @RequestParam(name = "price-end", required = false) BigDecimal priceEnd,
                                                                  @Parameter(description = "Optional flag for including active/inactive products", example = "true") @RequestParam(name = "active", required = false) Boolean active,
                                                                  Pageable pageable) {

        // Read multiple products by parameters.
        var products = productService.findAll(name, priceStart, priceEnd, active, pageable);
        var totalElements = products.getTotalNumber();

        // Set the total count header.
        var headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalElements));

        return new ResponseEntity<>(products, headers, HttpStatus.OK);
    }

    /**
     * Handles requests for deleting an existing product.
     *
     * @param id existing product id
     * @return {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT}
     * @throws NotFoundException when product does not exist
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @Operation(operationId = "deleteProduct", summary = "Deletes an existing product", description = "Handles requests for deleting an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<String> delete(@Parameter(description = "Product id", required = true)
                                         @PathVariable long id)
            throws NotFoundException {

        // Delete an existing product.
        productService.delete(id);
        logger.info("Deleted product with id: " + id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
