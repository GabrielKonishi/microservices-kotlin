package com.gabriel.libraryapi.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val errorMessage: String,
    val path: String
)
