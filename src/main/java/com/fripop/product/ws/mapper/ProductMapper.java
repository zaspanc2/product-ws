package com.fripop.product.ws.mapper;

import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductDto;
import com.fripop.product.ws.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * Mapper used for converting {@link Product}, {@link ProductDto} and {@link ProductCreateDto}.
 *
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Converts {@link ProductCreateDto} to {@link Product}.
     * @param productCreate {@link ProductCreateDto} to convert
     * @return constructed {@link Product}
     */
    @Named("ProductCreateToProduct")
    Product productCreateToProduct(ProductCreateDto productCreate);

    /**
     * Converts {@link Product} ti {@link ProductDto}.
     * @param product {@link Product} to convert
     * @return constructed {@link ProductDto}
     */
    @Named("ProductToProductDto")
    ProductDto productToProductDto(Product product);
}
