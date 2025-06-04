package com.gabriel.libraryapi.request

import com.gabriel.libraryapi.entity.Author
import java.time.LocalDate

data class BookRequest(
    val title: String,

    val publishedDate: LocalDate,

    val authorId: Long

)
