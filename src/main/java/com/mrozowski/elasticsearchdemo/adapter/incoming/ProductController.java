package com.mrozowski.elasticsearchdemo.adapter.incoming;

import com.mrozowski.elasticsearchdemo.domain.SimpleProductService;
import com.mrozowski.elasticsearchdemo.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/product")
@RequiredArgsConstructor
class ProductController {

  private final SimpleProductService simpleProductService;

  @PostMapping("/add")
  ResponseEntity<String> addProduct(@RequestBody Product product) {
    return ResponseEntity.ok(simpleProductService.addProduct(product));
  }

  @GetMapping("/find")
  ResponseEntity<Product> findById(@RequestParam("id") String id) {
    return ResponseEntity.ok(simpleProductService.findById(id));
  }
}
