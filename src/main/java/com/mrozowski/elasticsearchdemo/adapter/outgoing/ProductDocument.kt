package com.mrozowski.elasticsearchdemo.adapter.outgoing

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "products")
data class ProductDocument(
    @Id val id: String? = null,

    @Field(type = FieldType.Text, name = "name")
    val name: String,

    @Field(type = FieldType.Integer, name = "priceInCents")
    val priceInCents: Int,

    @Field(type = FieldType.Integer, name = "quantity")
    val quantity: Int,

    @Field(type = FieldType.Keyword, name = "category")
    val category: String,

    @Field(type = FieldType.Text, name = "desc")
    val description: String
)
