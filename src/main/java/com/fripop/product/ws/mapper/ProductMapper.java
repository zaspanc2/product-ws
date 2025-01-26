package com.fripop.product.ws.mapper;

import com.fripop.product.ws.dto.ProductCreateDto;
import com.fripop.product.ws.dto.ProductDto;
import com.fripop.product.ws.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Named("ProductCreateToProduct")
    Product productCreateToProduct(ProductCreateDto productCreate);

    @Named("ProductToProductDto")
    ProductDto productToProductDto(Product product);
}
