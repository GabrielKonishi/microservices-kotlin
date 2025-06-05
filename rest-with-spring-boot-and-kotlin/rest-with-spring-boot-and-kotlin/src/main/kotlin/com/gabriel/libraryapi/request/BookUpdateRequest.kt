package com.gabriel.libraryapi.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class BookUpdateRequest(

    @field:NotBlank(message = "title cannot be blank")
    val title: String,

    @NotNull
    val publishedDate: LocalDate,

    @NotNull
    val authorId: Long
)
