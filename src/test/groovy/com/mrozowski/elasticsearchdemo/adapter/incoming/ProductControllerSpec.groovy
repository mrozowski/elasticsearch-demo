package com.mrozowski.elasticsearchdemo.adapter.incoming

import com.fasterxml.jackson.databind.ObjectMapper
import com.mrozowski.elasticsearchdemo.domain.Fixtures
import com.mrozowski.elasticsearchdemo.domain.ProductService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ProductControllerSpec extends Specification {

  private MockMvc mockMvc
  def simpleProductService = Mock(ProductService)
  def underTest = new ProductController(simpleProductService)

  def setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()
  }

  def 'should return product based on given id'() {
    given:
    simpleProductService.findById(Fixtures.ID) >> Fixtures.PRODUCT

    expect:
    mockMvc.perform(get("/v1/product/find").param("id", Fixtures.ID))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.id').value(Fixtures.ID))
  }

  def 'should add a new product'() {
    given:
    def jsonRequest = new ObjectMapper().writeValueAsString(Fixtures.NEW_PRODUCT)
    simpleProductService.addProduct(Fixtures.NEW_PRODUCT) >> Fixtures.ID

    expect:
    mockMvc.perform(post("/v1/product/add")
        .content(jsonRequest)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(Fixtures.ID))
  }
}
