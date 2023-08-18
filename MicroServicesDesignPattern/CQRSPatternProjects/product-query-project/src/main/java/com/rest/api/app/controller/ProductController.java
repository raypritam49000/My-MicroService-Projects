package com.rest.api.app.controller;

import com.rest.api.app.dto.ProductDto;
import com.rest.api.app.dto.ProductEvent;
import com.rest.api.app.service.ProductQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ProductQueryService productQueryService;

    @Autowired
    public void setProductQueryService(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        return productQueryService.getProducts();
    }

    @KafkaListener(topics = "product-event-topic", groupId = "product-event-group")
    public void processProductEvent(ProductEvent productEvent) {

        LOGGER.info("@@@ Processing product event ::: "+productEvent);

        if (productEvent.getEventType().equals("CreateProduct")) {
            productQueryService.createProduct(productEvent.getProductDto());
        }

        if (productEvent.getEventType().equals("UpdateProduct")) {
            productQueryService.updateProduct(productEvent.getProductDto().getProductId(), productEvent.getProductDto());
        }

    }
}
