package com.google.shinyay.controller

import com.google.shinyay.entity.Book
import com.google.shinyay.repository.BookRepository
import com.google.shinyay.service.BookService
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController("/api/v1")
class BookController(val service: BookService) {

    @GetMapping("/books")
    fun getBook(@RequestParam isbn: String): ResponseEntity<Book> {
        return ResponseEntity<Book>(service.getBook(isbn), HttpStatus.OK)
    }

    @PostMapping("/books")
    fun saveBook(@RequestBody book: Book): ResponseEntity<Book> {
        return ResponseEntity<Book>(service.saveBook(book), HttpStatus.OK)
    }

}