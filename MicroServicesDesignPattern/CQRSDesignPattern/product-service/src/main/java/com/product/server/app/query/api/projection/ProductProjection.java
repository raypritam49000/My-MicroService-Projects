package com.product.server.app.query.api.projection;

import com.product.server.app.command.api.data.Product;
import com.product.server.app.command.api.model.ProductRestModel;
import com.product.server.app.command.api.repository.ProductRepository;
import com.product.server.app.query.api.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery) {

        LOGGER.info("@@@ ProductProjection QueryHandler ::: " + getProductsQuery);

        List<Product> products = productRepository.findAll();

        List<ProductRestModel> productRestModels =
                products.stream()
                        .map(product -> ProductRestModel
                                .builder()
                                .productId(product.getProductId())
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .name(product.getName())
                                .build())
                        .collect(Collectors.toList());

        LOGGER.info("@@@ ProductProjection productRestModels ::: " + productRestModels);

        return productRestModels;
    }
}
