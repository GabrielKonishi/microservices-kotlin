package com.gabriel.libraryapi.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "author")
open class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate)   {
    constructor() : this(null, "", LocalDate.MIN)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}
