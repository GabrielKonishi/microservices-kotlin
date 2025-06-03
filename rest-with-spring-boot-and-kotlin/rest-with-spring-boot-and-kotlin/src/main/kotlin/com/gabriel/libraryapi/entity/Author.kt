package com.gabriel.libraryapi.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val name: String,

    val birthDate: LocalDate
)
