package com.mrozowski.elasticsearchdemo.domain;

import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException;
import com.mrozowski.elasticsearchdemo.domain.model.Product;
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleProductService {

  private final ProductRepository productRepository;

  public Product findById(String id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  public String addProduct(Product product) {
    return productRepository.addProduct(product);
  }
}
