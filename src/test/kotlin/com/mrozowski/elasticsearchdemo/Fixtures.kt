package com.mrozowski.elasticsearchdemo

import com.mrozowski.elasticsearchdemo.domain.model.Product

class Fixtures {

    companion object {
        const val SEARCH_FRAZE = "apple"
        const val PRODUCT_ID = "productId1"
        const val PRODUCT_ID_2 = "productId2"
        const val NAME = "Laptop"
        const val DESCRIPTION = "Fast and powerful laptop with SSD"
        const val PRICE = 120000
        const val QUANTITY = 10
        val CATEGORY = Product.Category.ELECTRONICS
        val SUGGESTIONS = setOf("apple watch", "apple iphone")

        val PRODUCT: Product = Product(
            id = PRODUCT_ID,
            category = CATEGORY,
            description = DESCRIPTION,
            name = NAME,
            priceInCents = PRICE,
            quantity = QUANTITY
        )
        val PRODUCT_2: Product = Product(
            id = PRODUCT_ID_2,
            category = CATEGORY,
            description = "Noise cancelling headphones",
            name = "Headphones",
            priceInCents = 5000,
            quantity = 15
        )

        val PRODUCT_LIST = listOf(PRODUCT, PRODUCT_2)


        val NEW_PRODUCT: Product = Product(
            category = Product.Category.ELECTRONICS,
            description = "Noise cancelling headphones",
            name = "Headphones",
            priceInCents = 5000,
            quantity = 15
        )
    }
}