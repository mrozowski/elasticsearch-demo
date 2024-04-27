package com.mrozowski.elasticsearchdemo.adapter.outgoing

import com.mrozowski.elasticsearchdemo.domain.Fixtures
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import spock.lang.Specification

class ProductRepositoryAdapterSpec extends Specification {

  def esProductRepository = Mock(ElasticSearchProductRepository)
  def elasticsearchOperations = Mock(ElasticsearchOperations)
  def underTest = new ProductRepositoryAdapter(esProductRepository, elasticsearchOperations)

  def 'should get product from repository by id'() {
    when:
    def result = underTest.findById(Fixtures.ID)

    then:
    1 * esProductRepository.findById(Fixtures.ID) >> Optional.of(DocumentFixtures.PRODUCT_DOCUMENT)
    result == Optional.of(Fixtures.PRODUCT)
  }

  def 'should add new product to repository'() {
    when:
    def result = underTest.addProduct(Fixtures.NEW_PRODUCT)

    then:
    1 * esProductRepository.save(DocumentFixtures.NEW_PRODUCT_DOCUMENT) >> DocumentFixtures.PRODUCT_DOCUMENT
    result == Fixtures.PRODUCT.id()
  }
}
