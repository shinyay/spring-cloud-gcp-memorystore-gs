package com.google.shinyay.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

data class Book(@Id val isbn: String,
                var title: String)