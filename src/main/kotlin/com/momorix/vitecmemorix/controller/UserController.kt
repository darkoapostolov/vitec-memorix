package com.momorix.vitecmemorix.controller

import com.momorix.vitecmemorix.dto.UserCreationRequest
import com.momorix.vitecmemorix.dto.UserListDTO
import com.momorix.vitecmemorix.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userCreationRequest: UserCreationRequest): ResponseEntity<String> {
        userService.createUser(userCreationRequest)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    @GetMapping
    fun findUsers(
        @RequestParam(defaultValue = "") query: String,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<UserListDTO> {
        return ResponseEntity(userService.getUsers(query, limit), HttpStatus.OK)
    }
}