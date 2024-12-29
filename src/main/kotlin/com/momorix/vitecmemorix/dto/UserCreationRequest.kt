package com.momorix.vitecmemorix.dto

data class UserCreationRequest(
    val name: String,
    val email: String,
    val password: String
)