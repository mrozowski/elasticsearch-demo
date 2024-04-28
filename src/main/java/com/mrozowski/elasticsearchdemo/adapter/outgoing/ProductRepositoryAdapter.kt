package com.mrozowski.elasticsearchdemo.adapter.outgoing

import co.elastic.clients.elasticsearch._types.query_dsl.*
import com.mrozowski.elasticsearchdemo.domain.model.Product
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Component
import java.util.*

private val NAME_AND_DESCRIPTION_FIELDS = listOf("name", "desc")

@Component
internal class ProductRepositoryAdapter(
    private val repository: ElasticSearchProductRepository,
    private val elasticsearchOperations: ElasticsearchOperations
) : ProductRepository {

    override fun findById(id: String): Product? {
        return repository.findById(id).map { document: ProductDocument -> mapToProduct(document) }.orElse(null)
    }

    override fun addProduct(product: Product): String {
        return repository.save(mapToProductDocument(product)).id!!
    }

    override fun searchByNameOrDescription(value: String): List<Product> {
        val multiMatchQuery = MultiMatchQuery.of { query ->
            query.operator(Operator.And)
                .type(TextQueryType.CrossFields)
                .query(value)
                .fields(NAME_AND_DESCRIPTION_FIELDS)
        }

        val query = NativeQuery.builder()
            .withQuery { q -> q.multiMatch(multiMatchQuery) }
            .build()

        val searchHits = elasticsearchOperations.search(query, ProductDocument::class.java)
        return searchHits.stream()
            .map(SearchHit<ProductDocument>::getContent)
            .map(this::mapToProduct)
            .toList()
    }

    override fun suggest(fraze: String): List<String> {
        val wildcardQuery = WildcardQuery.of { query: WildcardQuery.Builder ->
            query.field("name").value("$fraze*")
        }
        val searchQuery = NativeQuery.builder()
            .withFilter { q -> q.wildcard(wildcardQuery) }
            .withPageable(PageRequest.of(0, 7))
            .build()

        val searchSuggestions = elasticsearchOperations.search(searchQuery, ProductDocument::class.java)
        return searchSuggestions.stream()
            .map(SearchHit<ProductDocument>::getContent)
            .map(ProductDocument::name)
            .toList()
    }

    private fun mapToProduct(document: ProductDocument): Product {
        return Product(
            id = document.id,
            category = Product.Category.valueOf(document.category),
            description = document.description,
            name = document.name,
            priceInCents = document.priceInCents,
            quantity = document.quantity
        )
    }

    private fun mapToProductDocument(product: Product): ProductDocument {
        return ProductDocument(
            name = product.name,
            category = product.category.name,
            description = product.description,
            priceInCents = product.priceInCents,
            quantity = product.quantity
        )
    }
}
