package com.mrozowski.elasticsearchdemo.adapter.incoming

import com.fasterxml.jackson.databind.ObjectMapper
import com.mrozowski.elasticsearchdemo.Fixtures
import com.mrozowski.elasticsearchdemo.domain.ProductService
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

private val mapper: ObjectMapper = ObjectMapper()

class ProductControllerTest : ShouldSpec({

    val productService = mock<ProductService>()
    val underTest = ProductController(productService)
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(underTest).build()

    should("add a new product") {
        whenever(productService.findById(Fixtures.PRODUCT_ID)).thenReturn(Fixtures.PRODUCT)

        mockMvc.perform(get("/v1/product/find").param("id", Fixtures.PRODUCT_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(Fixtures.PRODUCT_ID))
    }

    should("return product based on given id") {
        val jsonRequest = mapper.writeValueAsString(Fixtures.NEW_PRODUCT)
        whenever(productService.addProduct(Fixtures.NEW_PRODUCT)).thenReturn(Fixtures.PRODUCT_ID)

        mockMvc.perform(post("/v1/product/add")
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(Fixtures.PRODUCT_ID))
    }

    should("return list of products based on given fraze") {
        whenever(productService.searchByNameOrDescription(Fixtures.SEARCH_FRAZE)).thenReturn(Fixtures.PRODUCT_LIST)

        mockMvc.perform(get("/v1/product/findAll").param("fraze", Fixtures.SEARCH_FRAZE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(mapper.writeValueAsString(Fixtures.PRODUCT_LIST)))
    }

    should("return suggestions based on given fraze") {
        whenever(productService.suggest(Fixtures.SEARCH_FRAZE)).thenReturn(Fixtures.SUGGESTIONS)

        mockMvc.perform(get("/v1/product/suggestions").param("fraze", Fixtures.SEARCH_FRAZE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(mapper.writeValueAsString(Fixtures.SUGGESTIONS)))
    }

})
