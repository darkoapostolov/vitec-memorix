package com.momorix.vitecmemorix.service

import com.momorix.vitecmemorix.dto.UserCreationRequest
import com.momorix.vitecmemorix.dto.UserListDTO
import com.momorix.vitecmemorix.exception.BadRequestException
import com.momorix.vitecmemorix.extesions.mapToDTO
import com.momorix.vitecmemorix.model.User
import com.momorix.vitecmemorix.repository.UserRepository
import com.momorix.vitecmemorix.utils.AESUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    @Value("\${aes_key}")
    private var keyString: String? = null
    fun createUser(userCreationRequest: UserCreationRequest) {
        with(userCreationRequest) {
            userRepository.findUserByEmail(email)?.let {
                throw BadRequestException("Duplicate e-mail: $email")
            }
            val encryptedPassword = AESUtils.encrypt(password, keyString)
            userRepository.save(User(name, email, encryptedPassword))
        }
    }

    fun getUsers(query: String, limit: Int): UserListDTO {
        require(limit > 0) { "Page size must not be less than one" }

        val totalUsers = userRepository.count().toInt()
        val pageable = PageRequest.of(0, limit)
        val usersToList = userRepository.findAllByNameStartingWith(query, pageable)
            .map(User::mapToDTO)

        return UserListDTO(usersToList, totalUsers)
    }
}