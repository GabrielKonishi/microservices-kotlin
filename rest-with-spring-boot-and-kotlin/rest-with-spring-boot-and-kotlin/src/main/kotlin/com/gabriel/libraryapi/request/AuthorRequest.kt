package com.gabriel.libraryapi.request

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class AuthorRequest(
    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @NotNull
    val birthDate: LocalDate
    
)
