package com.momorix.vitecmemorix.model

import jakarta.persistence.*

@Entity
@Table(name = "memorix_users")
data class User(
    var name: String,
    var email: String,
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
