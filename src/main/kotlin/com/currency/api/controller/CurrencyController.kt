package com.currency.api.controller

import com.currency.api.currency.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController {
    @Autowired
    private lateinit var currencyService: CurrencyService

    @GetMapping("account")
    fun accountInfo() = this.currencyService.getAccountInfo()

    @GetMapping("priceChanged24h")
    fun priceChanged24h() = this.currencyService.getPriceChange24h()

    @GetMapping("myTrades")
    fun myTrades() = this.currencyService.getMyTrades()
}
