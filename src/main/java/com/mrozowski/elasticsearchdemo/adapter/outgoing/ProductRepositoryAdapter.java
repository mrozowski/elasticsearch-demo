package com.mrozowski.elasticsearchdemo.adapter.outgoing;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import com.mrozowski.elasticsearchdemo.domain.model.Product;
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ProductRepositoryAdapter implements ProductRepository {

  private static final List<String> NAME_AND_DESCRIPTION_FIELDS = List.of("name", "desc");
  private final ElasticSearchProductRepository repository;
  private final ElasticsearchOperations elasticsearchOperations;

  @Override
  public Optional<Product> findById(String id) {
    return repository.findById(id).map(this::mapToProduct);
  }

  @Override
  public String addProduct(Product product) {
    return repository.save(mapToProductDocument(product)).id();
  }

  @Override
  public List<Product> searchByNameOrDescription(String value) {
    var multiMatchQuery = MultiMatchQuery.of(query -> query.operator(Operator.And)
        .type(TextQueryType.CrossFields)
        .query(value)
        .fields(NAME_AND_DESCRIPTION_FIELDS));

    var query = NativeQuery.builder()
        .withQuery(q -> q.multiMatch(multiMatchQuery))
        .build();

    SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(query, ProductDocument.class);
    return searchHits.stream()
        .map(SearchHit::getContent)
        .map(this::mapToProduct)
        .toList();
  }

  @Override
  public List<String> suggest(String fraze) {
    var wildcardQuery = WildcardQuery.of(query -> query.field("name").value(fraze+"*"));
    var searchQuery = NativeQuery.builder()
        .withFilter(Query.of(q -> q.wildcard(wildcardQuery)))
        .withPageable(PageRequest.of(0, 7))
        .build();

    SearchHits<ProductDocument> searchSuggestions = elasticsearchOperations.search(searchQuery, ProductDocument.class);
    return searchSuggestions.stream()
        .map(SearchHit::getContent)
        .map(ProductDocument::name)
        .toList();
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
