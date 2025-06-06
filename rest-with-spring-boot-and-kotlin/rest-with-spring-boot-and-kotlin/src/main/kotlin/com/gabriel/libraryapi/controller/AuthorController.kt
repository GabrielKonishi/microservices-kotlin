package com.gabriel.libraryapi.controller

import com.gabriel.libraryapi.request.AuthorRequest
import com.gabriel.libraryapi.request.AuthorUpdateRequest
import com.gabriel.libraryapi.response.AuthorResponse
import com.gabriel.libraryapi.service.AuthorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {


    @GetMapping
    fun getAllAuthors() :
            ResponseEntity<List<AuthorResponse>>{
        val authors = authorService.getAllAuthor()
        return ResponseEntity.ok(authors)
    }

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: Long):
            ResponseEntity<AuthorResponse>{
        val author = authorService.getAuthorById(id)
        return ResponseEntity.ok(author)
    }

    @PostMapping("/save")
    fun  createAuthor(@RequestBody authorRequest: AuthorRequest):
            ResponseEntity<AuthorResponse>{
        val response = authorService.registerAuthor(authorRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: Long):
            ResponseEntity<Void> {
        authorService.deleteAuthorById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Long,
                     @Valid @RequestBody authorUpdateRequest: AuthorUpdateRequest):
            ResponseEntity<AuthorResponse> {
        val updatedAuthor = authorService.updateAuthor(id, authorUpdateRequest)
        return ResponseEntity.ok(updatedAuthor)
    }

}