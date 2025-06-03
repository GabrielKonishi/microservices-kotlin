package com.gabriel.libraryapi.controller

import com.gabriel.libraryapi.entity.Author
import com.gabriel.libraryapi.repository.AuthorRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorRepository: AuthorRepository) {

    @GetMapping
    fun getAllAuthors() : List<Author> = authorRepository.findAll()

    fun  createAuthor(@RequestBody author: Author): Author = authorRepository.save(author)
}