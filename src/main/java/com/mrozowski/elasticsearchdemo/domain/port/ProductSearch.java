package com.mrozowski.elasticsearchdemo.domain.port;

import com.mrozowski.elasticsearchdemo.domain.model.Product;
import org.springframework.data.domain.Page;

public interface ProductSearch {

  Page<Product> searchByNameOrDescription(String value);
}
