package com.google.shinyay.repository

interface BookRepository {
    fun getByTitle(title: String)
    fun getByIsbn(isbn: String)
}