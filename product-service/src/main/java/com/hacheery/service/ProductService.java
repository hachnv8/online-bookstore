package com.hacheery.service;

import com.hacheery.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void deleteProduct(Long id);

    List<Product> findProductsByName(String name);
}
