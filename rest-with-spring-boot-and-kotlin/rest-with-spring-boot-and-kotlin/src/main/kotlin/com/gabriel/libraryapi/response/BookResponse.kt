package com.gabriel.libraryapi.response

import java.time.LocalDate

data class BookResponse(
    val id: Long,
    val title: String,
    val publishedDate: LocalDate,
    val authorName: String
)
