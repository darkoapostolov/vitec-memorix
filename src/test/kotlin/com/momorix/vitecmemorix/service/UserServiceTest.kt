package com.momorix.vitecmemorix.service

import com.momorix.vitecmemorix.dto.UserCreationRequest
import com.momorix.vitecmemorix.exception.BadRequestException
import com.momorix.vitecmemorix.extesions.mapToDTO
import com.momorix.vitecmemorix.model.User
import com.momorix.vitecmemorix.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

class UserServiceTest {

    private val userRepository: UserRepository = mockk()
    private val userService = UserService(userRepository)

    @Nested
    inner class CreateUser {
        @Test
        fun `createUser should save a new user when email is not a duplicate`() {
            val keyStringField = UserService::class.java.getDeclaredField("keyString")
            keyStringField.isAccessible = true
            keyStringField.set(userService, "Rs-osd2Ks23jdnaL215skXjsn8v5kmqe")
            val userCreationRequest = UserCreationRequest(
                name = "Darko Apostolov",
                email = "darkoapostolov21@gmail.com",
                password = "password123"
            )

            every { userRepository.findUserByEmail(userCreationRequest.email) } returns null
            every { userRepository.save(any()) } returns User(
                name = userCreationRequest.name,
                email = userCreationRequest.email,
                password = "mockedEncryptedPassword"
            )

            userService.createUser(userCreationRequest)

            verify(exactly = 1) { userRepository.findUserByEmail(userCreationRequest.email) }
            verify(exactly = 1) { userRepository.save(any()) }
        }

        @Test
        fun `createUser should throw BadRequestException for duplicate email`() {
            val userCreationRequest = UserCreationRequest(
                name = "Darko Apostolov",
                email = "darkoapostolov21@gmail.com",
                password = "password123"
            )
            val existingUser = User(
                name = "Darko Apostolovski",
                email = "darkoapostolov22@gmail.com",
                password = "encryptedPassword"
            )

            every { userRepository.findUserByEmail(userCreationRequest.email) } returns existingUser

            val exception = assertThrows<BadRequestException> {
                userService.createUser(userCreationRequest)
            }

            assertEquals("Duplicate e-mail: darkoapostolov21@gmail.com", exception.message)
            verify(exactly = 1) { userRepository.findUserByEmail(userCreationRequest.email) }
            verify(exactly = 0) { userRepository.save(any()) }
        }
    }

    @Nested
    inner class FindUsers {
        @Test
        fun `getUsers should return users and total count`() {
            val query = "John"
            val limit = 10
            val pageable: Pageable = PageRequest.of(0, limit)
            val users = listOf(
                User("Darko Apostolov", "darkoapostolov21@gmail.com", "encryptedPassword"),
                User("Darko Apostolovski", "darkoapostolov22@gmail.com", "encryptedPassword")
            )

            every { userRepository.count() } returns users.size.toLong()
            every { userRepository.findAllByNameStartingWith(query, pageable) } returns users

            val result = userService.getUsers(query, limit)

            assertEquals(users.size, result.users.size)
            assertEquals(users.map { it.mapToDTO() }, result.users)
            verify(exactly = 1) { userRepository.count() }
            verify(exactly = 1) { userRepository.findAllByNameStartingWith(query, pageable) }
        }

        @Test
        fun `getUsers should throw IllegalArgumentException for invalid limit`() {
            val exception = assertThrows<IllegalArgumentException> {
                userService.getUsers("Darko", 0)
            }

            assertEquals("Page size must not be less than one", exception.message)
            verify(exactly = 0) { userRepository.count() }
            verify(exactly = 0) { userRepository.findAllByNameStartingWith(any(), any()) }
        }
    }
}
