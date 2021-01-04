package com.google.shinyay.repository

import com.google.shinyay.entity.Book
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<Book, String>{
}