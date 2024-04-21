package com.mrozowski.elasticsearchdemo.domain.port;

import com.mrozowski.elasticsearchdemo.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

  Optional<Product> findById(String id);

  String addProduct(Product product);
}
