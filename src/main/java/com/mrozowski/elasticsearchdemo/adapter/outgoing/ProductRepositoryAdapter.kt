package com.mrozowski.elasticsearchdemo.adapter.outgoing

import co.elastic.clients.elasticsearch._types.query_dsl.*
import com.mrozowski.elasticsearchdemo.domain.model.Product
import com.mrozowski.elasticsearchdemo.domain.model.SearchCommand
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
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

    override fun addProducts(products: List<Product>) {
        repository.saveAll(products.map(this::mapToProductDocument))
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

    override fun search(command: SearchCommand): Page<Product> {
        val queryBuilder = BoolQuery.Builder()
        command.fraze?.let { fraze ->
            val matchPhraseQuery = MatchPhraseQuery.of { query: MatchPhraseQuery.Builder ->
                query.field("name").query(fraze)
            }
            queryBuilder.must { q -> q.matchPhrase(matchPhraseQuery) }
        }?: run{
            val wildcardQuery = WildcardQuery.of { query: WildcardQuery.Builder ->
                query.field("name").value("*")
            }
            queryBuilder.must { q -> q.wildcard(wildcardQuery) }
        }

        command.priceFilter?.let {
            val range = RangeQuery.of { query ->
                query.field("priceInCents")
                    .from(it.priceRange.start.toString())
                    .to(it.priceRange.toString())

            }
            queryBuilder.must { q -> q.range(range) }
        }
        val page = PageRequest.of(command.page, command.size)
        val searchQuery = NativeQuery.builder()
            .withQuery { q -> q.bool(queryBuilder.build())}
            .withPageable(page)

            .build()

        val searchHits = elasticsearchOperations.search(searchQuery, ProductDocument::class.java)
        val hits = searchHits.stream()
            .map(SearchHit<ProductDocument>::getContent)
            .map(this::mapToProduct)
            .toList()

        return PageImpl(hits, page, searchHits.totalHits)
    }

    override fun suggest(fraze: String): Set<String> {
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
            .toList().toSet()
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
