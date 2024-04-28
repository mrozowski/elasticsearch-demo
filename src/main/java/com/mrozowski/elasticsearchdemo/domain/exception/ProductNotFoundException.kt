package com.mrozowski.elasticsearchdemo.domain.exception

class ProductNotFoundException(id: String) : RuntimeException(String.format(MESSAGE, id)) {
    companion object {
        private const val MESSAGE = "Product with id %s not found"
    }
}
