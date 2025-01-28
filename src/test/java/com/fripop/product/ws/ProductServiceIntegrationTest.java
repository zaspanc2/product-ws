package com.fripop.product.ws;

import com.fripop.product.ws.controller.ProductController;
import com.fripop.product.ws.dto.ObjectsResponseDto;
import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductDto;
import com.fripop.product.ws.dto.ProductUpdateDto;
import com.fripop.product.ws.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test scenarios for {@link ProductController}.
 * <p>
 * This test performs actual rest calls to real endpoints and will modify the data in the connected database.
 * Make sure that environment variables are set.
 *
 * @since 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductServiceIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProductRepository productRepository;

    private static Long PRODUCT_ID;

    /**
     * Clean up after tests.
     * <p>
     * Ensure that test product is deleted.
     */
    @AfterEach
    void cleanUp() {
        try {
            productRepository.deleteById(PRODUCT_ID);
        } catch (Exception e) {
            // Do nothing.
        }
    }

    /**
     * Test product crud endpoints.
     */
    @Test
    @DisplayName("Test product crud endpoints")
    void testProductCrudEndpoints() {

        // Test - Test the logic of a create product endpoint.
        // Product is expected to be created.

        // Create new product create dto.
        var productCreateDto = new ProductCreateDto();
        productCreateDto.setName("test-product");
        productCreateDto.setCode(UUID.randomUUID().toString());
        productCreateDto.setActive(true);
        productCreateDto.setPrice(new BigDecimal("33.4"));

        // Perform product create request and validate the response status.
        var createResponse = testRestTemplate.postForEntity("/products", productCreateDto, ProductDto.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        // Validate that product response is correct.
        var productDto = createResponse.getBody();
        assertNotNull(productDto, "Product content is expected.");
        assertNotNull(productDto.getId(), "Product id is expected.");
        assertNotNull(productDto.getCreated(), "Product created timestamp is expected to be set.");
        assertNull(productDto.getUpdated(), "Product updated timestamp is expected to be null.");
        assertEquals(productCreateDto.getName(), productDto.getName(), "Product name is expected to match.");
        assertEquals(productCreateDto.getCode(), productDto.getCode(), "Product code is expected to match.");
        assertEquals(productCreateDto.getActive(), productDto.getActive(), "Product active flag is expected to match.");
        assertEquals(productCreateDto.getPrice(), productDto.getPrice(), "Product price is expected to match.");

        // Mark product id to be cleared at the end.
        PRODUCT_ID = productDto.getId();

        // Test - Test the logic of a update product endpoint.
        // Create new product update dto.
        var productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setActive(false);
        productUpdateDto.setPrice(new BigDecimal("55.4"));
        productUpdateDto.setName("test-product-2");

        // Perform product update request and validate the response status.
        var requestEntity = new HttpEntity<>(productUpdateDto);
        var putResponse = testRestTemplate.exchange("/products/" + PRODUCT_ID, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.NO_CONTENT, putResponse.getStatusCode());

        // Test - Test the logic of a get product endpoint.
        var getResponse = testRestTemplate.getForEntity("/products/" + PRODUCT_ID, ProductDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        // Test - Test the logic of a get all product endpoint.
        var getAllResponse = testRestTemplate.getForEntity("/products", ObjectsResponseDto.class);
        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());

        // Test - Test the logic of a delete product endpoint.
        var deleteResponse = testRestTemplate.exchange("/products/" + PRODUCT_ID, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
