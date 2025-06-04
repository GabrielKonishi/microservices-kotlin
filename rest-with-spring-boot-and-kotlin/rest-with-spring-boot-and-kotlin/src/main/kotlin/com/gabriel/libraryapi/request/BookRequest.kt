package com.gabriel.libraryapi.request

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class BookRequest(
    @field:NotBlank(message = "title cannot be blank")
    val title: String,

    @NotNull
    val publishedDate: LocalDate,

    @NotNull
    val authorId: Long

)
