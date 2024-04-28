package com.mrozowski.elasticsearchdemo.domain.port

import com.mrozowski.elasticsearchdemo.domain.model.Product
import java.util.*

interface ProductRepository {
    fun findById(id: String): Product?
    fun addProduct(product: Product): String
    fun searchByNameOrDescription(value: String): List<Product>
    fun suggest(fraze: String): List<String>
}
