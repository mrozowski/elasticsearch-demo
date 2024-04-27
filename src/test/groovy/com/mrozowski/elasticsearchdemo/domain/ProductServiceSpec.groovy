package com.mrozowski.elasticsearchdemo.domain

import com.mrozowski.elasticsearchdemo.domain.exception.ProductNotFoundException
import com.mrozowski.elasticsearchdemo.domain.port.ProductRepository
import spock.lang.Specification

class ProductServiceSpec extends Specification {

  def productRepository = Mock(ProductRepository)
  def underTest = new ProductService(productRepository)

  def 'should add new product and returned generated id'() {
    when:
    def result = underTest.addProduct(Fixtures.NEW_PRODUCT)

    then:
    1 * productRepository.addProduct(Fixtures.NEW_PRODUCT) >> Fixtures.ID
    result == Fixtures.ID
  }

  def 'should find product by id'() {
    when:
    def result = underTest.findById(Fixtures.ID)

    then:
    1 * productRepository.findById(Fixtures.ID) >> Optional.of(Fixtures.PRODUCT)
    result == Fixtures.PRODUCT
  }

  def 'should return ProductNotFoundException when product not found'() {
    when:
    def result = underTest.findById(Fixtures.ID)

    then:
    1 * productRepository.findById(Fixtures.ID) >> Optional.empty()
    thrown ProductNotFoundException
  }
}
