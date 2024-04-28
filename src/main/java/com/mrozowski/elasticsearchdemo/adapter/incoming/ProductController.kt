package com.mrozowski.elasticsearchdemo.adapter.incoming

import com.mrozowski.elasticsearchdemo.domain.ProductService
import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException
import com.mrozowski.elasticsearchdemo.domain.model.Product
import com.mrozowski.elasticsearchdemo.domain.model.SearchCommand
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("v1/product")
internal class ProductController(private val productService: ProductService) {

    @PostMapping("/add")
    fun addProduct(@RequestBody product: Product): ResponseEntity<String> {
        return ResponseEntity.ok(productService.addProduct(product))
    }

    @GetMapping("/find")
    fun findById(@RequestParam("id") id: String): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.findById(id))
    }

    @GetMapping("/findAll")
    fun searchByNameOrDescription(@RequestParam("fraze") fraze: String): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(productService.searchByNameOrDescription(fraze))
    }


    @GetMapping("/suggestions")
    fun suggest(@RequestParam("fraze") fraze: String): ResponseEntity<List<String>> {
        return ResponseEntity.ok(productService.suggest(fraze))
    }

    @GetMapping("/search")
    fun suggest(@RequestBody command: SearchCommand): ResponseEntity<Page<Product>> {
        return ResponseEntity.ok(productService.search(command))
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleIDateTimeParseException(
        ex: ProductNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ApiError> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError(
                timestamp = LocalDateTime.now(),
                message = ex.message!!,
                path = request.requestURI,
                status = HttpStatus.NOT_FOUND
            )
        )
    }
}
