package com.mrozowski.elasticsearchdemo.domain.model

data class SearchCommand(
    val page: Int = 0,
    val size: Int = 10,
    val fraze: String? = null,
    val priceFilter: PriceFilter? = null,
    val category: Product.Category? = null
) {
    data class PriceFilter(
        val priceRange: Range,
        val descending: Boolean = false
    )

    data class Range(val start: Int, val end: Int)
}


