package com.momorix.vitecmemorix.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {
    private val logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)
    var errorResponse: MutableMap<String, String?> = HashMap()

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestExceptions(ex: BadRequestException): ResponseEntity<Map<String, String?>> {
        errorResponse["error"] = ex.message
        logger.error(errorResponse["error"])
        return ResponseEntity(errorResponse, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

}