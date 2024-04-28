package com.mrozowski.elasticsearchdemo.adapter.outgoing

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
internal interface ElasticSearchProductRepository : ElasticsearchRepository<ProductDocument, String>