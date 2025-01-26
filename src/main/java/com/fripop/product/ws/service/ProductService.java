package com.fripop.product.ws.service;

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

@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto create(ProductCreateDto productCreate) {
        var product = productMapper.productCreateToProduct(productCreate);
        productRepository.save(product);

        return productMapper.productToProductDto(product);
    }

    public void update(long id, ProductUpdateDto productUpdate) throws NotFoundException {
        var existingProduct = productRepository.findRequiredById(id);

        if (productUpdate.getActive() != null) existingProduct.setActive(productUpdate.getActive());
        if (productUpdate.getName() != null) existingProduct.setName(productUpdate.getName());
        if (productUpdate.getPrice() != null) existingProduct.setPrice(productUpdate.getPrice());

        productRepository.save(existingProduct);
    }

    public ProductDto findRequired(long id) throws NotFoundException {
        return productMapper.productToProductDto(productRepository.findRequiredById(id));
    }

    public List<ProductDto> findAll(String name, BigDecimal amountStart, BigDecimal amountEnd, Boolean active, Pageable pageable) {
        return productRepository.findAll(name, amountStart, amountEnd, active, pageable).getContent().stream()
                .map(productMapper::productToProductDto).collect(Collectors.toList());
    }

    public void delete(long id) throws NotFoundException {
        var existingProduct = productRepository.findRequiredById(id);
        productRepository.delete(existingProduct);
    }
}
