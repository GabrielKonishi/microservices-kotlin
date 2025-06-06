package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.entity.Book
import com.gabriel.libraryapi.exception.NotFoundException
import com.gabriel.libraryapi.repository.BookRepository
import com.gabriel.libraryapi.request.BookRequest
import com.gabriel.libraryapi.request.BookUpdateRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.util.Optional
import kotlin.test.assertEquals

class BookServiceTest {
    private val bookId = 1L
    private val invalidBookId = 999L
    private val authorId = 1L
    private val repository: BookRepository = mock()
    private val authorService: AuthorService = mock()
    private val service = BookService(repository, authorService)

    @Test
    fun `should create book succesfully`() {
        val author: Author = buildAuthor()

        `when`(authorService.findAuthorById(authorId))
            .thenReturn(buildAuthor())

        `when`(repository.save(any()))
            .thenReturn(buildBook(
                id = 1L,
                title = "Test Book",
                publishedDate = LocalDate.of(2021, 1, 1),
                author = buildAuthor()
            ))

        val result = service.registerBook(buildBookRequest())

        assertEquals(1L, result.id)
        assertEquals("Test Book", result.title)
        assertEquals(LocalDate.of(2021, 1, 1), result.publishedDate)
        assertEquals(author.name, result.authorName)

        verify(repository, times(1)).save(any())

    }

    @Test
    fun `should get all book succesfully`() {
        val author = buildAuthor()

        val bookList = listOf(
            buildBook(
                id = 1L,
                title = "Test Book",
                publishedDate = LocalDate.of(2021, 1, 1),
                author = author),
            buildBook(
                id = 2L,
                title = "Test Book 2",
                publishedDate = LocalDate.of(2022, 2, 2),
                author = author
            ))

        `when`(repository.findAll()).thenReturn(bookList)

        val result = service.getAllBooks()

        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertEquals("Test Book", result[0].title)
        assertEquals(2L, result[1].id)
        assertEquals("Test Book 2", result[1].title)

        verify(repository, times(1)).findAll()

    }

    @Test
    fun `should delete book succesfully`() {
        val book = buildBook()

        `when`(repository.findById(bookId))
            .thenReturn(Optional.of(book))

        service.deleteBookById(bookId)

        verify(repository, times(1)).findById(bookId)
        verify(repository, times(1)).delete(book)
    }

    @Test
    fun `should update book succesfully`() {
        val author = buildAuthor()
        val originalBook = buildBook()
        val bookUpdateRequest = buildBookUpdateRequest()
        val updatedBook = Book(
            bookId,
            bookUpdateRequest.title,
            bookUpdateRequest.publishedDate,
            author)

        `when`(repository.findById(bookId))
            .thenReturn(Optional.of(originalBook))

        `when`(authorService.findAuthorById(authorId))
            .thenReturn(author)

        `when`(repository.save(any()))
            .thenReturn(updatedBook)

        val result = service.updateBook(bookId, bookUpdateRequest)

        assertEquals(1L, result.id)
        assertEquals("Update Test Book", result.title)
        assertEquals(LocalDate.of(2022, 2, 2), result.publishedDate)

        verify(repository, times(1)).findById(bookId)
        verify(repository,times(1)).save(updatedBook)

    }

    @Test
    fun `should get book by Id succesfully`() {
        val book = buildBook()

        `when`(repository.findById(bookId))
            .thenReturn(Optional.of(book))

        var result = service.findBookById(bookId)

        assertEquals(1L, result.id)
        assertEquals("Test Book", result.title)
        assertEquals(LocalDate.of(2021, 1, 1), result.publishedDate)

        verify(repository, times(1)).findById(bookId)

    }

    @Test
    fun`should not found any book`() {
        `when`(repository.findById(invalidBookId))
            .thenReturn(Optional.empty())

        val exception = assertThrows<NotFoundException> {
            service.findBookById(invalidBookId)
        }

        assertEquals("Book was not found with the specied Id: 999", exception.message)

    }

    @Test
    fun`should not found any book on deleting`() {
        `when`(repository.findById(invalidBookId))
            .thenReturn(Optional.empty())

        val exception = assertThrows<NotFoundException> {
            service.deleteBookById(invalidBookId)
        }

        assertEquals("Book was not found with the specied Id: 999", exception.message)

        verify(repository, times(1)).findById(invalidBookId)
        verify(repository, never()).deleteById(invalidBookId)
    }

    @Test
    fun`should not found any book on update`() {
        `when`(repository.findById(invalidBookId))
            .thenReturn(Optional.empty())

        val exception = assertThrows<NotFoundException> {
            service.updateBook(invalidBookId, buildBookUpdateRequest())
        }

        assertEquals("Book was not found with the specied Id: 999", exception.message)

        verify(repository, times(1)).findById(invalidBookId)
        verify(repository, never()).save(any())
    }

    private fun buildBook(
        id: Long = 1L,
        title: String = "Test Book",
        publishedDate: LocalDate = LocalDate.of(2021, 1, 1),
        author: Author = buildAuthor()): Book {
        return Book(
            id = id,
            title = title,
            publishedDate = publishedDate,
            author = author)
    }

    private fun buildBookRequest(
        title: String = "Test Book",
        publishedDate: LocalDate = LocalDate.of(2021, 1, 1),
        authorId: Long = 1L
    ): BookRequest {
        return BookRequest(
            title = title,
            publishedDate = publishedDate,
            authorId = authorId)
    }

    private fun buildBookUpdateRequest(
        title: String = "Update Test Book",
        publishedDate: LocalDate = LocalDate.of(2022, 2, 2),
        authorId: Long = 1L
    ): BookUpdateRequest {
        return BookUpdateRequest(
            title = title,
            publishedDate = publishedDate,
            authorId = authorId
        )
    }

    private fun buildAuthor(
        id: Long = 1L,
        name: String = "Test Author",
        birthDate: LocalDate = LocalDate.of(1990, 1, 1)): Author {
        return Author(
            id = id,
            name = name,
            birthDate = birthDate)
    }

}