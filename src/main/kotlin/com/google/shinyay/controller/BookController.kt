package com.google.shinyay.controller

import com.google.shinyay.entity.Book
import com.google.shinyay.repository.BookRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("/api/v1")
class BookController(val repository: BookRepository) {

    @Cacheable(value = ["books"], key = "isbn")
    @GetMapping("/books/{isbn}")
    fun getBook(@PathVariable isbn: String): Optional<Book> {
        return repository.findById(isbn)
    }
}