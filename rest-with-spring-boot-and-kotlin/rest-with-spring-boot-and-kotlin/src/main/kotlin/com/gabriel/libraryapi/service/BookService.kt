package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.entity.Book
import com.gabriel.libraryapi.exception.NotFoundException
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
        val author = authorRepository.findById(bookRequest.authorId)
            .orElseThrow{
                RuntimeException("Author Not Found with id ${bookRequest.authorId}") }

        val saved = bookRepository.save(bindingBook(bookRequest, author))
        return BookMapper.toResponse(saved)

    }


    fun bindingBook(bookRequest: BookRequest, author: Author):Book{
         val book = Book(
            title = bookRequest.title,
            publishedDate = bookRequest.publishedDate,
            author = author
        )
        return book
    }

    fun getAllBooks(): List<BookResponse> {
        return bookRepository.findAll().map {
            book ->
            BookResponse(
                id = requireNotNull(book.id),
                title = book.title,
                publishedDate = book.publishedDate,
                authorName = book.author.name
            )
        }

    }

    fun getBookById(id: Long): BookResponse {
        val book = bookRepository.findById(id).orElseThrow{
            NotFoundException("Book was not found with the specied Id: $id")
        }

        return BookResponse(
            requireNotNull(book.id),
            book.title,
            book.publishedDate,
            book.author.name)
    }


}