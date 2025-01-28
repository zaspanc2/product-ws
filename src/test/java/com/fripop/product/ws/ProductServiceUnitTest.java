package com.fripop.product.ws;

import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductUpdateDto;
import com.fripop.product.ws.exception.NotFoundException;
import com.fripop.product.ws.mapper.ProductMapper;
import com.fripop.product.ws.mapper.ProductMapperImpl;
import com.fripop.product.ws.model.Product;
import com.fripop.product.ws.repository.ProductRepository;
import com.fripop.product.ws.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.when;

/**
 * Unit scenarios for {@link ProductService}.
 *
 * @since 1.0.0
 */
public class ProductServiceUnitTest {

    private ProductMapper productMapper;
    private ProductService productService;
    private ProductRepository productRepository;

    /**
     * Prepare mocks.
     */
    @BeforeEach
    void setUp() {

        // Create a new product mapper implementation to do the actual mappings.
        productMapper = new ProductMapperImpl();

        // Mock product repository to return the default values.
        productRepository = Mockito.mock(ProductRepository.class);

        // Mock product service to call real methods.
        productService = Mockito.mock(ProductService.class, Mockito.withSettings()
                .useConstructor(productRepository, productMapper).defaultAnswer(CALLS_REAL_METHODS));
    }

    /**
     * Test product mapstruct mappings.
     */
    @Test
    @DisplayName("Test product mapstruct mappings")
    void testProductMappings() {

        // Test - Mapping of product create dto to product.
        // Mapped product is expected to have all fields from product create dto set.

        // Create new product create dto.
        var productCreateDto = new ProductCreateDto();
        productCreateDto.setName("test");
        productCreateDto.setCode("test-1234");
        productCreateDto.setActive(true);
        productCreateDto.setPrice(new BigDecimal("33.4"));

        // Preform mapping and validate that all fields were set.
        var product = productMapper.productCreateToProduct(productCreateDto);
        assertEquals(productCreateDto.getName(), product.getName(), "Product name is expected to match.");
        assertEquals(productCreateDto.getCode(), product.getCode(), "Product code is expected to match.");
        assertEquals(productCreateDto.getActive(), product.getActive(), "Product active flag is expected to match.");
        assertEquals(productCreateDto.getPrice(), product.getPrice(), "Product price is expected to match.");

        // Test - Mapping of product to product dto.
        // Mapped product dto is expected to have all fields from product set.

        // Create new product create dto.
        product = new Product();
        product.setId(1L);
        product.setCode("test-1234");
        product.setName("test");
        product.setCreated(Timestamp.valueOf(LocalDateTime.of(2025, 1, 27, 12, 10, 10)));
        product.setUpdated(Timestamp.valueOf(LocalDateTime.of(2025, 1, 27, 12, 50, 50)));
        product.setActive(true);
        product.setPrice(productCreateDto.getPrice());

        // Preform mapping and validate that all fields were set.
        var productDto = productMapper.productToProductDto(product);
        assertEquals(product.getId(), productDto.getId(), "Product id is expected to match.");
        assertEquals(product.getCode(), productDto.getCode(), "Product code is expected to match.");
        assertEquals(product.getName(), productDto.getName(), "Product name is expected to match.");
        assertEquals(product.getCreated(), productDto.getCreated(), "Product created is expected to match.");
        assertEquals(product.getUpdated(), productDto.getUpdated(), "Product updated is expected to match.");
        assertEquals(product.getActive(), productDto.getActive(), "Product active flag is expected to match.");
        assertEquals(product.getPrice(), productDto.getPrice(), "Product price is expected to match.");
    }

    /**
     * Test product update method.
     *
     * @throws NotFoundException that is not expected
     */
    @Test
    @DisplayName("Test product mapstruct mappings")
    void testProductUpdate() throws NotFoundException {

        // Make find call to the product repository return the following product.
        var existingProduct = new Product();
        existingProduct.setId(1L);
        when(productRepository.findRequiredById(anyLong())).thenReturn(existingProduct);

        // Prepare the product update data.
        var productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setName("test-update");
        productUpdateDto.setPrice(new BigDecimal("123.41"));
        productUpdateDto.setActive(false);

        // Preform mock product update.
        productService.update(1L, productUpdateDto);
    }
}
