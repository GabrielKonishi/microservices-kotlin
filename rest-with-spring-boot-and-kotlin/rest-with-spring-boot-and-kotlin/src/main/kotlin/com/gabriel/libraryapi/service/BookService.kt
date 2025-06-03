package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.entity.Book
import com.gabriel.libraryapi.mapper.BookMapper
import com.gabriel.libraryapi.repository.AuthorRepository
import com.gabriel.libraryapi.repository.BookRepository
import com.gabriel.libraryapi.request.BookRequest
import com.gabriel.libraryapi.response.BookResponse
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
    ) {

    fun create(bookRequest: BookRequest): BookResponse {
        val author = authorRepository.findById(bookRequest.author)
            .orElseThrow{ RuntimeException("Author Not Found") }

        val saved = bookRepository.save(BookMapper.toEntity(bookRequest, author))
        return BookMapper.toResponse(saved)

    }



}