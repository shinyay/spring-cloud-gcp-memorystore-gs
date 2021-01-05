package com.google.shinyay.service

import com.google.shinyay.entity.Book
import com.google.shinyay.repository.BookRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(val repository: BookRepository) {

    @Cacheable(value = ["books"], key = "isbn")
    fun getBook(isbn: String): Optional<Book> {
        return repository.findById(isbn)
    }

    fun saveBook(book: Book): Book {
        return repository.save(book)
    }
}