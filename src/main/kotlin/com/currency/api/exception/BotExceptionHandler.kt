package com.currency.api.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class BotExceptionHandler {
    @ExceptionHandler(CurrencyException::class)
    fun vehicleIsExistsExceptionHandler(
        ex: CurrencyException,
        request: WebRequest
    ): ResponseEntity<BotException> {
        val errorMessage = BotException(
            ex.statusCode.value(),
            ex.message.toString(),
            Date()
        )
        return ResponseEntity<BotException>(errorMessage, ex.statusCode)
    }
}
