package com.mrozowski.elasticsearchdemo.adapter.incoming;

import com.mrozowski.elasticsearchdemo.domain.ProductService;
import com.mrozowski.elasticsearchdemo.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/product")
@RequiredArgsConstructor
class ProductController {

  private final ProductService productService;

  @PostMapping("/add")
  ResponseEntity<String> addProduct(@RequestBody Product product) {
    return ResponseEntity.ok(productService.addProduct(product));
  }

  @GetMapping("/find")
  ResponseEntity<Product> findById(@RequestParam("id") String id) {
    return ResponseEntity.ok(productService.findById(id));
  }

  @GetMapping("/findAll")
  ResponseEntity<List<Product>> searchByNameOrDescription(@RequestParam("fraze") String fraze) {
    return ResponseEntity.ok(productService.searchByNameOrDescription(fraze));
  }

  @GetMapping("/suggestions")
  ResponseEntity<List<String>> suggest(@RequestParam("fraze") String fraze){
    return ResponseEntity.ok(productService.suggest(fraze));
  }
}
