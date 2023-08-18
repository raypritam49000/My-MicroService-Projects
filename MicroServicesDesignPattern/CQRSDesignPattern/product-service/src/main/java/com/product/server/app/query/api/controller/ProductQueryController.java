package com.product.server.app.query.api.controller;

import com.product.server.app.command.api.model.ProductRestModel;
import com.product.server.app.query.api.queries.GetProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<ProductRestModel> getAllProducts() {
        LOGGER.info("@@@ ProductQueryController getAllProducts :::");
        GetProductsQuery getProductsQuery = new GetProductsQuery();

        List<ProductRestModel> productRestModels =
                queryGateway.query(getProductsQuery,
                                ResponseTypes.multipleInstancesOf(ProductRestModel.class))
                        .join();

        LOGGER.info("@@@ ProductQueryController getAllProducts ::: " + productRestModels);
        return productRestModels;
    }
}
