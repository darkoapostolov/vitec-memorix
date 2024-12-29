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
        errorResponse["message"] = ex.message
        errorResponse["status"] = HttpStatus.BAD_REQUEST.value().toString()
        logger.error(errorResponse["message"])
        return ResponseEntity(errorResponse, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

}