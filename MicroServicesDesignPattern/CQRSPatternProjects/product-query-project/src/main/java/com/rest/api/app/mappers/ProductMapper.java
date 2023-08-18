package com.rest.api.app.mappers;

import com.rest.api.app.dto.ProductDto;
import com.rest.api.app.enity.Product;
import org.springframework.beans.BeanUtils;

public class ProductMapper {

    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
