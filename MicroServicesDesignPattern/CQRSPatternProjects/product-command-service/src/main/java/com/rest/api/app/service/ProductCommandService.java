package com.rest.api.app.service;

import com.rest.api.app.dto.ProductDto;
import com.rest.api.app.dto.ProductEvent;

public interface ProductCommandService {
    public ProductDto createProduct(ProductEvent productEvent);
    public ProductDto updateProduct(String productId, ProductEvent productEvent);
}
