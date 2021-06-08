package com.currency.api.currency

import com.currency.api.dto.AccountCurrency
import com.currency.api.dto.Asset
import com.currency.api.dto.PriceChanged24h
import com.currency.api.exception.CurrencyException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*


@Service
class CurrencyService(
    @Value("\${currency.http.url}")
    private val url: String,

    @Autowired
    private val hmacSerive: HMACSignature
) {
    private val LOG = LoggerFactory.getLogger(javaClass)

    fun getAccountInfo(): AccountCurrency? {
        val builder = UriComponentsBuilder.fromHttpUrl("$url/account")
            .queryParam("timestamp", Date().time)
        builder.queryParam("signature", hmacSerive.createSignature(builder.toUriString().split("?")[1]))
        LOG.info("currency.com get account info")
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                hmacSerive.getHeader(),
                AccountCurrency::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get account info", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun getPriceChange24h(): PriceChanged24h? {
        val builder = UriComponentsBuilder.fromHttpUrl("$url/ticker/24hr")
            .queryParam("symbol", "${Asset.ETH.name}/${Asset.USD.name}")
        LOG.info("currency.com get price changed last 24 hours")
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                hmacSerive.getHeaderWithoutApiKey(),
                PriceChanged24h::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get 24 hours price changed", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun getMyTrades(): String? {
        val builder = UriComponentsBuilder.fromHttpUrl("$url/myTrades")
            .queryParam("timestamp", Date().time)
            .queryParam("symbol", "${Asset.ETH.name}/${Asset.USD.name}")
        builder.queryParam("signature", hmacSerive.createSignature(builder.toUriString().split("?")[1]))
        LOG.info("currency.com get my trades")
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                hmacSerive.getHeader(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get my trades", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }
}
