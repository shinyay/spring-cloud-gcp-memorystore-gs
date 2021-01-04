package com.google.shinyay.repository

import com.google.shinyay.entity.Book
import org.springframework.stereotype.Component

@Component
class SimpleBookRepository : BookRepository {
    override fun getAllBooks(): MutableList<Book> {
        TODO("Not yet implemented")
    }

    override fun getByTitle(title: String): Book {
        TODO("Not yet implemented")
    }

    override fun getByIsbn(isbn: String): Book {
        TODO("Not yet implemented")
    }
}