package com.currency.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class CurrencyException(status: HttpStatus, message: String) :
    HttpStatusCodeException(status, message)
