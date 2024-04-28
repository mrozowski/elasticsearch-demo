package com.mrozowski.elasticsearchdemo.domain

import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException
import com.mrozowski.elasticsearchdemo.domain.model.Product
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun findById(id: String): Product {
        return productRepository.findById(id)?: throw ProductNotFoundException(id)
    }

    fun searchByNameOrDescription(value: String): List<Product> {
        return productRepository.searchByNameOrDescription(value)
    }

    fun addProduct(product: Product): String {
        return productRepository.addProduct(product)
    }

    fun suggest(fraze: String): List<String> {
        return productRepository.suggest(fraze)
    }
}
