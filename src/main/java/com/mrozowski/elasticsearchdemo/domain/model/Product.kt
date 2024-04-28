package com.mrozowski.elasticsearchdemo.domain.model

data class Product(
    val id: String? = null,
    val name: String,
    val description: String,
    val category: Category,
    val quantity: Int,
    val priceInCents: Int
) {
    enum class Category {
        ELECTRONICS,
        APPLIANCES,
        CLOTHES
    }
}
