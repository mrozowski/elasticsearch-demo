package com.mrozowski.elasticsearchdemo.domain;

import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException;
import com.mrozowski.elasticsearchdemo.domain.model.Product;
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Product findById(String id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  public List<Product> searchByNameOrDescription(String value) {
    return productRepository.searchByNameOrDescription(value);
  }

  public String addProduct(Product product) {
    return productRepository.addProduct(product);
  }

  public List<String> suggest(String fraze) {
    return productRepository.suggest(fraze);
  }
}
