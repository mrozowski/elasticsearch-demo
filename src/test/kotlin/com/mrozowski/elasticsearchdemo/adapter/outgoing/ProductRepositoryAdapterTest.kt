package com.mrozowski.elasticsearchdemo.adapter.outgoing

import com.mrozowski.elasticsearchdemo.Fixtures
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import java.util.*

class ProductRepositoryAdapterTest : ShouldSpec({

    val esProductRepository = mock<ElasticSearchProductRepository>()
    val elasticsearchOperations = mock<ElasticsearchOperations>()
    val underTest = ProductRepositoryAdapter(esProductRepository, elasticsearchOperations)

    should("should get product from repository by id") {
        whenever(esProductRepository.findById(Fixtures.PRODUCT_ID)).thenReturn(Optional.of(productDocument))
        val result = underTest.findById(Fixtures.PRODUCT_ID)

        result shouldBe Fixtures.PRODUCT
    }

    should("should add product") {
        whenever(esProductRepository.save(newProductDocument)).thenReturn(productDocument)
        val result = underTest.addProduct(Fixtures.PRODUCT)

        result shouldBe Fixtures.PRODUCT.id
    }
})


private val productDocument =
    ProductDocument(
        id = Fixtures.PRODUCT_ID,
        name = Fixtures.NAME,
        category = Fixtures.CATEGORY.name,
        description = Fixtures.DESCRIPTION,
        priceInCents = Fixtures.PRICE,
        quantity = Fixtures.QUANTITY
    )

private val newProductDocument =
    ProductDocument(
        name = Fixtures.NAME,
        category = Fixtures.CATEGORY.name,
        description = Fixtures.DESCRIPTION,
        priceInCents = Fixtures.PRICE,
        quantity = Fixtures.QUANTITY
    )