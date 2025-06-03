package com.gabriel.libraryapi.mapper

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.entity.Book
import com.gabriel.libraryapi.request.BookRequest
import com.gabriel.libraryapi.response.BookResponse

object BookMapper {
    fun toEntity(bookRequest: BookRequest, author: Author): Book {
        return Book(
            title = bookRequest.title,
            publishedDate = bookRequest.publishedDate,
            author = author
        )
    }

    fun toResponse(bookEntity: Book): BookResponse{
        return BookResponse(
            id = requireNotNull(bookEntity.id) { "Book ID must not be null when mapping to BookResponse" },
            title = bookEntity.title,
            publishedDate = bookEntity.publishedDate,
            authorName = bookEntity.author.name
        )
    }
}