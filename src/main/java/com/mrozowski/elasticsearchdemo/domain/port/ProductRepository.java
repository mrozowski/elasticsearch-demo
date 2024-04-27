package com.mrozowski.elasticsearchdemo.domain.port;

import com.mrozowski.elasticsearchdemo.domain.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

  Optional<Product> findById(String id);

  String addProduct(Product product);

  List<Product> searchByNameOrDescription(String value);

  List<String> suggest(String fraze);
}
