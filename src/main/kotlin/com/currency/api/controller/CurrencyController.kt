package com.currency.api.controller

import com.currency.api.analyze.MyCurrencyService
import com.currency.api.currency.CurrencyPublicApi
import com.currency.api.dto.Asset
import com.currency.api.dto.MyCurrency
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController {
    @Autowired
    private lateinit var currencyPublicApi: CurrencyPublicApi

    @Autowired
    private lateinit var myCurrency: MyCurrencyService

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

    @GetMapping("my-currency")
    fun myCurrency(@RequestParam asset: Asset) = this.myCurrency.getCurrency(asset)

    @PostMapping
    fun setMyCurrency(@RequestParam asset: Asset, @RequestBody currency: MyCurrency) =
        this.myCurrency.setCurrency(asset, currency)
}
