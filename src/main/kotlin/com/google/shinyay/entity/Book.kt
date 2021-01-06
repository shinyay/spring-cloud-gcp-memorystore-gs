package com.google.shinyay.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("book")
data class Book(@Id val isbn: String,
                var title: String) : Serializable