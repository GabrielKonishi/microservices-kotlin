package com.gabriel.libraryapi.repository

import com.gabriel.libraryapi.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long>{
    fun findByTitleContainingIgnoreCase(title: String): List<Book>
}