package com.gabriel.libraryapi.controller

import com.gabriel.libraryapi.request.BookRequest
import com.gabriel.libraryapi.response.BookResponse
import com.gabriel.libraryapi.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookResponse>>{
        val books = bookService.getAllBooks()
        return ResponseEntity.ok(books)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): ResponseEntity<BookResponse>{
        val book = bookService.getBookById(id)
        return ResponseEntity.ok(book)
    }

    @PostMapping("/save")
    fun createBook(@RequestBody bookRequest: BookRequest): ResponseEntity<BookResponse>{
        val response = bookService.create(bookRequest)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
    
}
