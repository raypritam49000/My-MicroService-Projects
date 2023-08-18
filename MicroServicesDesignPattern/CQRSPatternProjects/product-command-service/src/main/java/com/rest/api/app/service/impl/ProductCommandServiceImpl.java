package com.rest.api.app.service.impl;

import com.rest.api.app.dto.ProductDto;
import com.rest.api.app.dto.ProductEvent;
import com.rest.api.app.enity.Product;
import com.rest.api.app.mappers.ProductMapper;
import com.rest.api.app.repository.ProductRepository;
import com.rest.api.app.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private ProductRepository productRepository;
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ProductDto createProduct(ProductEvent productEvent) {
        ProductDto createProduct = ProductMapper.entityToDto(productRepository.save(ProductMapper.dtoToEntity(productEvent.getProductDto())));
        ProductEvent event = new ProductEvent("CreateProduct", createProduct);
        kafkaTemplate.send("product-event-topic", event);
        return createProduct;
    }

    @Override
    public ProductDto updateProduct(String productId, ProductEvent productEvent) {
        Product product = productRepository.findById(productId).get();
        product.setDescription(productEvent.getProductDto().getDescription());
        product.setName(productEvent.getProductDto().getName());
        product.setPrice(productEvent.getProductDto().getPrice());
        ProductDto updateProduct = ProductMapper.entityToDto(productRepository.save(product));
        ProductEvent event = new ProductEvent("UpdateProduct", updateProduct);
        kafkaTemplate.send("product-event-topic", event);
        return updateProduct;
    }
}
