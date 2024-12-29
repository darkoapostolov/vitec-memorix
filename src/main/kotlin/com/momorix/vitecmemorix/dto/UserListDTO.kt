package com.momorix.vitecmemorix.dto

data class UserListDTO(
    val users: List<UserDTO>,
    val total: Int
)