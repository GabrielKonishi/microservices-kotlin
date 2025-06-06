package com.gabriel.libraryapi.controller

import com.gabriel.libraryapi.request.BookRequest
import com.gabriel.libraryapi.request.BookUpdateRequest
import com.gabriel.libraryapi.response.BookResponse
import com.gabriel.libraryapi.service.BookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookResponse>> {
        val books = bookService.getAllBooks()
        return ResponseEntity.ok(books)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): ResponseEntity<BookResponse> {
        val book = bookService.getBookById(id)
        return ResponseEntity.ok(book)
    }

    @PostMapping("/save")
    fun createBook(@Valid @RequestBody bookRequest: BookRequest): ResponseEntity<BookResponse> {
        val response = bookService.registerBook(bookRequest)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @DeleteMapping("/{id}")
    fun deleteBookById(@PathVariable id: Long): ResponseEntity<Void> {
        bookService.deleteBookById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Long,
                   @RequestBody @Valid request: BookUpdateRequest):
            ResponseEntity<BookResponse> {
        val updated = bookService.updateBook(id, request)
        return ResponseEntity.ok(updated)
    }
    
}
