package com.gabriel.libraryapi.request

import java.time.LocalDate

data class BookRequest(
    val title: String,

    val publishedDate: LocalDate,

    val author: Long

)
