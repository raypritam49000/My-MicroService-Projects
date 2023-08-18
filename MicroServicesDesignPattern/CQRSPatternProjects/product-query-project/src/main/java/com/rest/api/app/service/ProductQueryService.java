package com.rest.api.app.service;

import com.rest.api.app.dto.ProductDto;

import java.util.List;

public interface ProductQueryService {
    public List<ProductDto> getProducts();
    public ProductDto updateProduct(String productId, ProductDto productDto);
    public ProductDto createProduct(ProductDto productDto);
}
