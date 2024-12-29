package com.momorix.vitecmemorix.repository

import com.momorix.vitecmemorix.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findUserByEmail(email: String): User?

    fun findAllByNameStartingWithIgnoreCase(name: String, pageable: Pageable): List<User>

}