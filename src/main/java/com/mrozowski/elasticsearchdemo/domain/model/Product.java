package com.mrozowski.elasticsearchdemo.domain.model;

import lombok.Builder;

@Builder
public record Product(String id, String name, String description, Category category, int quantity, int priceInCents) {


  static public enum Category {
    ELECTRONICS,
    APPLIANCES,
    CLOTHES
  }
}
