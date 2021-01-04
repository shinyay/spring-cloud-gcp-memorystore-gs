package com.google.shinyay.repository

import com.google.shinyay.entity.Book

interface BookRepository {
    fun getAllBooks(): MutableList<Book>
    fun getByTitle(title: String): Book
    fun getByIsbn(isbn: String): Book
}