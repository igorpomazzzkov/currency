package com.currency.api.controller

import com.currency.api.currency.CurrencyPublicApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController {
    @Autowired
    private lateinit var currencyPublicApi: CurrencyPublicApi

    @GetMapping
    fun main(): String {
        return "Currency automation"
    }

    @GetMapping("assets")
    fun assets() = this.currencyPublicApi.assets()

    @GetMapping("ohlc")
    fun ohlc() = this.currencyPublicApi.ohlc()

    @GetMapping("orderBook")
    fun orderBook() = this.currencyPublicApi.orderBook()

    @GetMapping("summary")
    fun summary() = this.currencyPublicApi.summary()

    @GetMapping("ticker")
    fun ticker() = this.currencyPublicApi.ticker()

    @GetMapping("trades")
    fun trades() = this.currencyPublicApi.trades()
}
