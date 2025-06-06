package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.exception.NotFoundException
import com.gabriel.libraryapi.repository.AuthorRepository
import com.gabriel.libraryapi.request.AuthorRequest
import com.gabriel.libraryapi.request.AuthorUpdateRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.LocalDate
import java.util.Optional
import kotlin.test.assertEquals

class AuthorServiceTest {

    private val repository: AuthorRepository = mock()
    private val service = AuthorService(repository)

    @Test
    fun `should create author succesfully`() {
        val request = buildAuthorRequest()
        val expectedAuthor = buildAuthor()

        `when`(repository.save(any()))
            .thenReturn(expectedAuthor)

        val result = service.registerAuthor(request)

        assertEquals(1L, result.id)
        assertEquals("Test Author", result.name)
        assertEquals(LocalDate.of(1990, 1, 1), result.birthDate)

        verify(repository).save(any())
    }

    @Test
    fun `should get all author succesfully`() {
        val author = listOf(
            buildAuthor(1L,
                "Test Author",
                LocalDate.of(1990, 1, 1)),
            buildAuthor(2L,
                "Test Author 2",
                LocalDate.of(1991, 2, 2)))

        `when`(repository.findAll()).thenReturn(author)

        val result = service.getAllAuthor()

        assertEquals(2, result.size)
        assertEquals("Test Author", result[0].name)
        assertEquals(1L, result[0].id)
        assertEquals("Test Author 2", result[1].name)
        assertEquals(2L, result[1].id)

        verify(repository, times(1)).findAll()

    }

    @Test
    fun `should delete founded author succesfully`() {
        val authorId = 1L
        val author = buildAuthor()

        `when`(repository.findById(authorId))
            .thenReturn(Optional.of(author))

        service.deleteAuthorById(authorId)

        verify(repository).findById(authorId)
        verify(repository).delete(author)


    }

    @Test
    fun `should update founded author succesfully`() {
        val authorId = 1L
        val existingAuthor = buildAuthor()
        val authorUpdateRequest = buildUpdateAuthorRequest()

        val updatedAuthor = buildAuthor(
            id = authorId,
            name = authorUpdateRequest.name,
            birthDate = authorUpdateRequest.birthDate
        )

        `when`(repository.findById(authorId))
            .thenReturn(Optional.of(existingAuthor))

        `when`(repository.save(existingAuthor)).thenReturn(updatedAuthor)

        val result = service.updateAuthor(authorId, authorUpdateRequest)

        assertEquals(authorId,
            result.id)
        assertEquals("Updated Author Name",
            result.name)
        assertEquals(LocalDate.of(2002, 2, 2),
            result.birthDate)

        verify(repository, times(1))
            .findById(authorId)
        verify(repository, times(1))
            .save(updatedAuthor)

    }

    @Test
    fun `should get author by id succesfully`() {
        val authorId = 1L
        val author = buildAuthor()

        `when`(repository.findById(authorId)).thenReturn(Optional.of(author))

        val result = service.getAuthorById(authorId)

        assertEquals(1L, result.id)
        assertEquals("Test Author", result.name)
        assertEquals(LocalDate.of(1990, 1, 1), result.birthDate)

        verify(repository, times(1)).findById(authorId)

    }

    @Test
    fun `should not found any author`() {
        val authorId = 999L
        `when`(repository.findById(authorId)).thenReturn(Optional.empty())

        val exception = assertThrows<NotFoundException> {
            service.findAuthorById(authorId)
        }

        assertEquals("Author with id: 999 was not found", exception.message)

    }

    @Test
    fun `should not found author on deleting`() {
        val authorId = 999L

        `when`(repository.findById(authorId))
            .thenReturn(Optional.empty())

        val exception = assertThrows<NotFoundException> {
            service.deleteAuthorById(authorId)
        }

        assertEquals("Author with id: 999 was not found", exception.message)

        verify(repository).findById(authorId)
        verify(repository, never()).delete(any())
    }

    @Test
    fun `should not found author on updating method`() {
        val authorId = 999L

        `when`(repository.findById(authorId))
            .thenReturn(Optional.empty())

        val exception = assertThrows<NotFoundException> {
            service.updateAuthor(authorId, buildUpdateAuthorRequest())
        }

        assertEquals("Author with id: 999 was not found", exception.message)

        verify(repository).findById(authorId)
        verify(repository, never()).save(any())
    }

    private fun buildAuthorRequest(
        name: String = "Test Author",
        birthDate: LocalDate = LocalDate.of(1990, 1, 1)
    ): AuthorRequest {
        return AuthorRequest(
            name = name,
            birthDate = birthDate
        )
    }

    private fun buildUpdateAuthorRequest(
        name: String = "Updated Author Name",
        birthDate: LocalDate = LocalDate.of(2002, 2, 2)
    ): AuthorUpdateRequest{
        return AuthorUpdateRequest(
            name = name,
            birthDate = birthDate
        )
    }

    private fun buildAuthor(
        id: Long = 1L,
        name: String = "Test Author",
        birthDate: LocalDate = LocalDate.of(1990, 1, 1)
    ): Author {
        return Author(
            id = id,
            name = name,
            birthDate = birthDate
        )
    }
}