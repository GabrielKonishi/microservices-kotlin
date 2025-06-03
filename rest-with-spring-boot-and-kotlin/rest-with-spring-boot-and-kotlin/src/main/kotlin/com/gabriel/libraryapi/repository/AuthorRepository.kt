package com.gabriel.libraryapi.repository

import com.gabriel.libraryapi.entity.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long> {
}