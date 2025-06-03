package com.gabriel.libraryapi.response

import java.time.LocalDate

data class AuthorResponse(
    var id: Long,

    val name: String,

    val birthDate: LocalDate

)
