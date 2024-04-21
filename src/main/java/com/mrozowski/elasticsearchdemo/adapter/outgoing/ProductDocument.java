package com.mrozowski.elasticsearchdemo.adapter.outgoing;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Document(indexName = "products")
record ProductDocument(
    @Id String id,
    @Field(type = FieldType.Text, name = "name")
    String name,

    @Field(type = FieldType.Integer, name = "priceInCents")
    Integer priceInCents,

    @Field(type = FieldType.Integer, name = "quantity")
    Integer quantity,

    @Field(type = FieldType.Keyword, name = "category")
    String category,

    @Field(type = FieldType.Text, name = "desc")
    String description
) {
}
