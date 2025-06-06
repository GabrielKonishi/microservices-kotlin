package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.entity.Book
import com.gabriel.libraryapi.exception.NotFoundException
import com.gabriel.libraryapi.mapper.BookMapper
import com.gabriel.libraryapi.repository.BookRepository
import com.gabriel.libraryapi.request.BookRequest
import com.gabriel.libraryapi.request.BookUpdateRequest
import com.gabriel.libraryapi.response.BookResponse
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorService: AuthorService) {

    fun registerBook(bookRequest: BookRequest): BookResponse {
        val author = authorService.findAuthorById(bookRequest.authorId)
        val savedBook = bookRepository.save(bindingBook(bookRequest, author))
        return BookMapper.toResponse(savedBook)

    }


    fun getAllBooks(): List<BookResponse> {
        return bookRepository.findAll().map {
                book -> BookResponse(
            id = requireNotNull(book.id),
            title = book.title,
            publishedDate = book.publishedDate,
            authorName = book.author.name)
        }

    }

    fun deleteBookById(id: Long) {
        val book = findBookById(id)
        bookRepository.delete(book)
    }

    fun updateBook(id: Long, request: BookUpdateRequest):
            BookResponse {
        val book = findBookById(id)
        val author = authorService.findAuthorById(request.authorId)

        book.title = request.title
        book.publishedDate = request.publishedDate
        book.author = author

        val updated = bookRepository.save(book)

        return BookResponse(
            id = requireNotNull(updated.id),
            title = updated.title,
            publishedDate = updated.publishedDate,
            authorName = updated.author.name
        )
    }

    fun getBookById(id: Long): BookResponse {
        val book = findBookById(id)

        return BookResponse(
            requireNotNull(book.id),
            book.title,
            book.publishedDate,
            book.author.name)
    }

    fun bindingBook(bookRequest: BookRequest, author: Author): Book {
         val book = Book(
            title = bookRequest.title,
            publishedDate = bookRequest.publishedDate,
            author = author)
        return book
    }

    fun findBookById(id: Long): Book {
        val book = bookRepository.findById(id).orElseThrow {
            NotFoundException(message = "Book was not found with the specied Id: $id")
        }
        return book
    }

}