package com.mrozowski.elasticsearchdemo.domain

import com.mrozowski.elasticsearchdemo.domain.model.Product

class Fixtures {

  static final ID = "jAhfAI8BiWNNqzLKBS7a"

  static final NEW_PRODUCT = Product.builder()
      .name("Laptop")
      .category(Product.Category.ELECTRONICS)
      .description("Fast and powerful laptop with SSD")
      .quantity(10)
      .priceInCents(120000)
      .build()

  static final PRODUCT = Product.builder()
      .id(ID)
      .name("Laptop")
      .category(Product.Category.ELECTRONICS)
      .description("Fast and powerful laptop with SSD")
      .quantity(10)
      .priceInCents(120000)
      .build()
}