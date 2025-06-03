package com.gabriel.libraryapi.service

import com.gabriel.libraryapi.mapper.AuthorMapper
import com.gabriel.libraryapi.mapper.BookMapper
import com.gabriel.libraryapi.repository.AuthorRepository
import com.gabriel.libraryapi.request.AuthorRequest
import com.gabriel.libraryapi.response.AuthorResponse
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    fun create(authorRequest: AuthorRequest): AuthorResponse{
        val savedAuthor = authorRepository.save(AuthorMapper.toEntity(authorRequest))

        return AuthorMapper.toResponse(savedAuthor)
    }


}