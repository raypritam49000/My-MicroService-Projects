package com.rest.api.app.controller;

import com.rest.api.app.dto.ProductDto;
import com.rest.api.app.dto.ProductEvent;
import com.rest.api.app.service.ProductCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ProductCommandService productCommandService;

    @Autowired
    public void setProductCommandService(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductEvent productEvent) {
        LOGGER.info("@@@ createProduct ::: productEvent ::: " + productEvent);
        return productCommandService.createProduct(productEvent);
    }

    @PutMapping("/{productId}")
    public ProductDto updateProduct(@PathVariable("productId") String productId, @RequestBody ProductEvent productEvent) {
        LOGGER.info("@@@ updateProduct ::: id ::: " + productId + " productEvent ::: " + productEvent);
        return productCommandService.updateProduct(productId, productEvent);
    }
}
