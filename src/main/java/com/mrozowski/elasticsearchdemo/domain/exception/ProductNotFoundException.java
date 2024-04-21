package com.mrozowski.elasticsearchdemo.domain.exception;

public class ProductNotFoundException extends RuntimeException {
  private static final String MESSAGE = "Product with id %s not found";

  public ProductNotFoundException(String id) {
    super(String.format(MESSAGE, id));
  }
}
