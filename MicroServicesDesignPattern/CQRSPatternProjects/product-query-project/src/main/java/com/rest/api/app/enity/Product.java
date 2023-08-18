package com.rest.api.app.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "PRODUCT_QUERY")
@Entity
public class Product {
    @Id
    private String productId;
    private String name;
    private String description;
    private double price;
}
