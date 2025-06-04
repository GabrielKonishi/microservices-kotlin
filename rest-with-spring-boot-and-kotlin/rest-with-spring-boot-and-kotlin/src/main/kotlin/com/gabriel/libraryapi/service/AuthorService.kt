package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.exception.NotFoundException
import com.gabriel.libraryapi.mapper.AuthorMapper
import com.gabriel.libraryapi.repository.AuthorRepository
import com.gabriel.libraryapi.request.AuthorRequest
import com.gabriel.libraryapi.response.AuthorResponse
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    fun registerAuthor(authorRequest: AuthorRequest): AuthorResponse{
        val savedAuthor = authorRepository.save(AuthorMapper.toEntity(authorRequest))

        return AuthorMapper.toResponse(savedAuthor)
    }

    fun getAllAuthor(): List<AuthorResponse>{
        return authorRepository.findAll()
            .map { author ->
                AuthorResponse(
                    id = requireNotNull(author.id),
                    name = author.name,
                    birthDate = author.birthDate
                )
            }
    }

    fun getAuthorById(id: Long): AuthorResponse {
        val author = authorRepository.findById(id).orElseThrow {
            NotFoundException("Author with id: $id was not found.")
        }
        return AuthorResponse(requireNotNull(
            author.id),
            author.name,
            author.birthDate)
    }


}