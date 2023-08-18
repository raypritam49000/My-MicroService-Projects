package com.rest.api.app.service.impl;

import com.rest.api.app.dto.ProductDto;
import com.rest.api.app.dto.ProductEvent;
import com.rest.api.app.enity.Product;
import com.rest.api.app.mappers.ProductMapper;
import com.rest.api.app.repository.ProductRepository;
import com.rest.api.app.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductQueryService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(product -> ProductMapper.entityToDto(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return ProductMapper.entityToDto(productRepository.save(ProductMapper.dtoToEntity(productDto)));
    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).get();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return ProductMapper.entityToDto(productRepository.save(product));
    }
}
