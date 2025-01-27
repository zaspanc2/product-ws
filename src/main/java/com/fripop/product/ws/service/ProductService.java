package com.fripop.product.ws.service;

import com.fripop.product.ws.dto.ObjectsResponseDto;
import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductDto;
import com.fripop.product.ws.dto.ProductUpdateDto;
import com.fripop.product.ws.exception.NotFoundException;
import com.fripop.product.ws.mapper.ProductMapper;
import com.fripop.product.ws.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ProductService} is a service responsible for handling product related requests.
 *
 * @since 1.0.0
 */
@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Creates a new product.
     *
     * @param productCreate {@link ProductCreateDto} with creation information
     * @return created {@link ProductDto}
     */
    public ProductDto create(ProductCreateDto productCreate) {
        var product = productMapper.productCreateToProduct(productCreate);
        productRepository.save(product);
        return productMapper.productToProductDto(product);
    }

    /**
     * Updates an existing product.
     *
     * @param id            product id
     * @param productUpdate {@link ProductUpdateDto} with update information
     * @throws NotFoundException when product does not exist
     */
    public void update(long id, ProductUpdateDto productUpdate) throws NotFoundException {
        var existingProduct = productRepository.findRequiredById(id);

        existingProduct.setActive(productUpdate.getActive());
        existingProduct.setName(productUpdate.getName());
        existingProduct.setPrice(productUpdate.getPrice());

        productRepository.save(existingProduct);
    }

    /**
     * Finds product by id.
     *
     * @param id product id
     * @return existing {@link ProductDto}
     * @throws NotFoundException when product does not exist
     */
    @Transactional(readOnly = true)
    public ProductDto findRequired(long id) throws NotFoundException {
        return productMapper.productToProductDto(productRepository.findRequiredById(id));
    }

    /**
     * Finds all products by parameters.
     *
     * @param name       optional part or full name of a product
     * @param priceStart optional product price start
     * @param priceEnd   optional product price end
     * @param active     optional flag for including active/inactive products
     * @param pageable   {@link Pageable} with pagination information
     * @return {@link ObjectsResponseDto} containing a collection of {@link ProductDto}s
     */
    @Transactional(readOnly = true)
    public ObjectsResponseDto<ProductDto> findAll(String name, BigDecimal priceStart, BigDecimal priceEnd, Boolean active, Pageable pageable) {
        var productsPage = productRepository.findAll(name, priceStart, priceEnd, active, pageable);

        // Get the total number of elements.
        var totalElements = productsPage.getTotalElements();

        // Get the products on the current page.
        var products = productsPage.getContent().stream().map(productMapper::productToProductDto).collect(Collectors.toList());

        return new ObjectsResponseDto<>(totalElements, products);
    }

    /**
     * Deletes a product by id.
     *
     * @param id product id
     * @throws NotFoundException when product does not exist
     */
    public void delete(long id) throws NotFoundException {
        var existingProduct = productRepository.findRequiredById(id);
        productRepository.delete(existingProduct);
    }
}
