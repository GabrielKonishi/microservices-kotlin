package com.gabriel.libraryapi.request

import java.time.LocalDate

data class AuthorRequest(
    val name: String,

    val birthDate: LocalDate
    
)
