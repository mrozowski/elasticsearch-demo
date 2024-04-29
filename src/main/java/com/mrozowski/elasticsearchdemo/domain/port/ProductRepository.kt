package com.mrozowski.elasticsearchdemo.domain.port

import com.mrozowski.elasticsearchdemo.domain.model.Product
import com.mrozowski.elasticsearchdemo.domain.model.SearchCommand
import org.springframework.data.domain.Page

interface ProductRepository {
    fun findById(id: String): Product?
    fun addProduct(product: Product): String
    fun searchByNameOrDescription(value: String): List<Product>
    fun search(command: SearchCommand): Page<Product>
    fun suggest(fraze: String): Set<String>
    fun addProducts(products: List<Product>)
}
