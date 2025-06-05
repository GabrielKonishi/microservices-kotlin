package com.gabriel.libraryapi.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "author")
open class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate)   {
    constructor() : this(null, "", LocalDate.MIN) }
