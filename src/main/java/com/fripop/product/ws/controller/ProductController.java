package com.fripop.product.ws.controller;

import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductDto;
import com.fripop.product.ws.dto.ProductUpdateDto;
import com.fripop.product.ws.exception.NotFoundException;
import com.fripop.product.ws.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    @Operation(operationId = "createProduct", summary = "Creates a new product", description = "Handles requests for creating a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<ProductDto> create(@Parameter(description = "Product to be created", required = true)
                                             @Valid @NotNull @RequestBody ProductCreateDto productCreate) {

        var response = productService.create(productCreate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @Operation(operationId = "createProduct", summary = "Creates a new product", description = "Handles requests for creating a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
            })
    })
    public ResponseEntity<String> update(@Parameter(description = "Product id", required = true)
                                         @PathVariable long id,
                                         @Parameter(description = "Product to be updated", required = true)
                                         @Valid @NotNull @RequestBody ProductUpdateDto productUpdate)
            throws NotFoundException {
        productService.update(id, productUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ProductDto> find(@PathVariable long id) throws NotFoundException {
        var response = productService.findRequired(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProductDto>> findAll(Pageable pageable) {
        var response = productService.findAll(null, null, null, null, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
