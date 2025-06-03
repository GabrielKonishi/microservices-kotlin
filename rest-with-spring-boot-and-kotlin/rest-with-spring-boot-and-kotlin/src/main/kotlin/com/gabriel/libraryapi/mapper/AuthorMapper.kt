package com.gabriel.libraryapi.mapper

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.request.AuthorRequest
import com.gabriel.libraryapi.response.AuthorResponse

object AuthorMapper {
    fun toEntity(authorRequest: AuthorRequest): Author{
        return Author(
            name = authorRequest.name,
            birthDate = authorRequest.birthDate
        )
    }

    fun toResponse(author: Author): AuthorResponse{
        return AuthorResponse(
            id = requireNotNull(author.id) { "Author ID must not be null when mapping to AuthorResponse" },
            name = author.name,
            birthDate = author.birthDate
        )
    }

}