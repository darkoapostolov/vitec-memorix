package com.momorix.vitecmemorix.controller

import com.momorix.vitecmemorix.dto.UserCreationRequest
import com.momorix.vitecmemorix.dto.UserDTO
import com.momorix.vitecmemorix.dto.UserListDTO
import com.momorix.vitecmemorix.service.UserService
import io.mockk.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

class UserControllerTest {

    private val userService: UserService = mockk()
    private val userController: UserController = UserController(userService)

    @Nested
    inner class CreateUser {
        @Test
        fun `createUser should return 202 ACCEPTED when user is created`() {
            val userCreationRequest = UserCreationRequest(
                name = "Darko Apostolov",
                email = "darkoapostolov21@gmail.com",
                password = "password123"
            )

            every { userService.createUser(userCreationRequest) } just Runs

            val response = userController.createUser(userCreationRequest)

            assertEquals(HttpStatus.ACCEPTED, response.statusCode)
            verify(exactly = 1) { userService.createUser(userCreationRequest) }
        }
    }

    @Nested
    inner class FindUsers {
        @Test
        fun `findUsers should return 200 OK with user list`() {
            val query = "Darko"
            val limit = 10
            val userList = listOf(
                UserDTO(name = "Darko Apostolov", email = "darkoapostolov21@gmail.com"),
                UserDTO(name = "Darko Apostolovski", email = "darkoapostolov22@gmail.com")
            )
            val userListDTO = UserListDTO(users = userList, total = 2)

            every { userService.getUsers(query, limit) } returns userListDTO

            val response = userController.findUsers(query, limit)

            assertEquals(HttpStatus.OK, response.statusCode)
            assertEquals(userListDTO, response.body)
            verify(exactly = 1) { userService.getUsers(query, limit) }
        }
    }
}
