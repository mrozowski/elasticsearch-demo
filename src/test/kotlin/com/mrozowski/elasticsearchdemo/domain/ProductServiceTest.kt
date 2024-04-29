package com.mrozowski.elasticsearchdemo.domain

import com.mrozowski.elasticsearchdemo.Fixtures
import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ProductServiceTest : ShouldSpec({

    val productRepository = mock<ProductRepository>()
    val underTest = ProductService(productRepository)

    should("return product by id when found") {
        whenever(productRepository.findById(Fixtures.PRODUCT_ID)).thenReturn(Fixtures.PRODUCT)

        val result = underTest.findById(Fixtures.PRODUCT_ID)

        result shouldBe Fixtures.PRODUCT
    }

    should("throw ProductNotFoundException when product not found") {
        whenever(productRepository.findById(Fixtures.PRODUCT_ID)).thenReturn(null)

        val exception = shouldThrow<ProductNotFoundException> {
            underTest.findById(Fixtures.PRODUCT_ID)
        }

        exception.message shouldBe "Product with id ${Fixtures.PRODUCT_ID} not found"
    }

    should("return products matching name or description") {
        whenever(productRepository.searchByNameOrDescription(Fixtures.SEARCH_FRAZE)).thenReturn(Fixtures.PRODUCT_LIST)

        val foundProducts = underTest.searchByNameOrDescription(Fixtures.SEARCH_FRAZE)

        foundProducts shouldBe Fixtures.PRODUCT_LIST
    }

    should("add product and return its ID") {
        whenever(productRepository.addProduct(Fixtures.PRODUCT)).thenReturn(Fixtures.PRODUCT_ID)

        val returnedId = underTest.addProduct(Fixtures.PRODUCT)

        returnedId shouldBe Fixtures.PRODUCT_ID
    }

    should("return suggestions based on phrase") {
        val phrase = "apple"
        val suggestions = setOf("apple watch", "apple iphone")
        whenever(productRepository.suggest(phrase)).thenReturn(suggestions)

        val suggestedItems = underTest.suggest(phrase)

        suggestedItems shouldBe suggestions
    }
})
