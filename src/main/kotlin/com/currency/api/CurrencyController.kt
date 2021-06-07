package com.currency.api

import com.currency.api.currency.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController {
    @Autowired
    private lateinit var currencyService: CurrencyService

    @GetMapping
    fun main() = this.currencyService.getAccountInfo()
}
