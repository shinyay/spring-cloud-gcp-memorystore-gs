package com.google.shinyay.service

import com.google.shinyay.entity.Book
import com.google.shinyay.repository.BookRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(val repository: BookRepository) {

    @Cacheable(value = ["book"], key = "#isbn")
    fun getBook(isbn: String): Book? {
        return repository.findById(isbn).orElse(null)
    }

    fun saveBook(book: Book): Book {
        return repository.save(book)
    }

    @CacheEvict(value = ["book"], key = "#isbn")
    fun deleteBookByIsbn(isbn: String) {
        repository.deleteById(isbn)
    }

    @CachePut(value = ["book"], key = "#book.isbn")
    fun updateBook(book: Book): Book {
        return repository.save(book)
    }
}