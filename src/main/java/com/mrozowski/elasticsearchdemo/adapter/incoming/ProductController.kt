package com.mrozowski.elasticsearchdemo.adapter.incoming

import com.mrozowski.elasticsearchdemo.domain.ProductService
import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException
import com.mrozowski.elasticsearchdemo.domain.model.Product
import com.mrozowski.elasticsearchdemo.domain.model.SearchCommand
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("v1/product")
internal class ProductController(private val productService: ProductService) {

    @PostMapping("/add")
    fun addProduct(@RequestBody product: Product): ResponseEntity<String> {
        return ResponseEntity.ok(productService.addProduct(product))
    }

    @PostMapping("/addAll")
    fun addProduct(@RequestBody products: List<Product>): ResponseEntity<String> {
        productService.addProducts(products)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/find")
    fun findById(@RequestParam("id") id: String): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.findById(id))
    }

    @GetMapping("/findAll")
    fun searchByNameOrDescription(@RequestParam("fraze") fraze: String): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(productService.searchByNameOrDescription(fraze))
    }


    @CrossOrigin(value = ["http://localhost:63342/"])
    @GetMapping("/suggestions")
    fun suggest(@RequestParam("fraze") fraze: String): ResponseEntity<Set<String>> {
        return ResponseEntity.ok(productService.suggest(fraze))
    }

    @CrossOrigin(value = ["http://localhost:63342/"])
    @GetMapping("/search")
    fun search(
        @RequestParam("page") page: Int = 0,
        @RequestParam("size") size: Int = 10,
        @RequestParam("fraze") fraze: String? = null
    ): ResponseEntity<Page<Product>> {
        logger.info { "Received request to search products with fraze $fraze" }
        val command = SearchCommand(
            page = page,
            size = size,
            fraze = fraze
        )
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
