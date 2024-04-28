package com.mrozowski.elasticsearchdemo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ElasticsearchDemoApplication

    fun main(args: Array<String>) {
        SpringApplication.run(ElasticsearchDemoApplication::class.java, *args)
    }

