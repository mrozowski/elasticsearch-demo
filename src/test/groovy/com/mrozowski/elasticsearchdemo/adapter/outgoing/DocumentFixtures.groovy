package com.mrozowski.elasticsearchdemo.adapter.outgoing

import com.mrozowski.elasticsearchdemo.domain.Fixtures

class DocumentFixtures {

  static final NEW_PRODUCT_DOCUMENT = ProductDocument.builder()
      .name("Laptop")
      .category("ELECTRONICS")
      .description("Fast and powerful laptop with SSD")
      .quantity(10)
      .priceInCents(120000)
      .build()

  static final PRODUCT_DOCUMENT = ProductDocument.builder()
      .id(Fixtures.ID)
      .name("Laptop")
      .category("ELECTRONICS")
      .description("Fast and powerful laptop with SSD")
      .quantity(10)
      .priceInCents(120000)
      .build()
}
