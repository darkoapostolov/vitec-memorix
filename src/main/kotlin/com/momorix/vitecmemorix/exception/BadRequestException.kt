package com.momorix.vitecmemorix.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class BadRequestException(message: String?) : RuntimeException(message)