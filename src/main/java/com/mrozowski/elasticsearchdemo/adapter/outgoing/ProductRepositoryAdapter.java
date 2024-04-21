package com.mrozowski.elasticsearchdemo.adapter.outgoing;

import com.mrozowski.elasticsearchdemo.domain.model.Product;
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class ProductRepositoryAdapter implements ProductRepository {

  private final ElasticSearchProductRepository repository;

  @Override
  public Optional<Product> findById(String id) {
    return repository.findById(id).map(this::mapToProduct);
  }

  @Override
  public String addProduct(Product product) {
    return repository.save(mapToProductDocument(product)).id();
  }

  private Product mapToProduct(ProductDocument document) {
    return Product.builder()
        .id(document.id())
        .category(Product.Category.valueOf(document.category()))
        .description(document.description())
        .name(document.name())
        .priceInCents(document.priceInCents())
        .quantity(document.quantity())
        .build();
  }

  private ProductDocument mapToProductDocument(Product product) {
    return ProductDocument.builder()
        .name(product.name())
        .category(product.category().name())
        .description(product.description())
        .priceInCents(product.priceInCents())
        .quantity(product.quantity())
        .build();
  }
}
