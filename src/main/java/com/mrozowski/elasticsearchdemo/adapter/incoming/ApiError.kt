package com.mrozowski.elasticsearchdemo.adapter.incoming

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiError(
    val status: HttpStatus,
    val message: String,
    val path: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") val timestamp: LocalDateTime
)
