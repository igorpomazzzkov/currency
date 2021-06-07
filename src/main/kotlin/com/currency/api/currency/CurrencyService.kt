package com.currency.api.currency

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CurrencyService(
    @Value("\${currency.url}")
    private val url: String,

    @Value("\${currency.api.key}")
    private val apiKey: String,

    @Value("\${currency.secret.key}")
    private val secretKey: String
) {

}
