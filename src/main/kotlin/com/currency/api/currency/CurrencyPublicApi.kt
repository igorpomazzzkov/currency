package com.currency.api.currency

import com.currency.api.dto.Asset
import com.currency.api.exception.CurrencyException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class CurrencyPublicApi(
    @Value("\${currency.http.public.url}")
    private val url: String,
) {
    private val LOG = LoggerFactory.getLogger(javaClass)

    fun assets(): String? {
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                "$url/assets",
                HttpMethod.GET,
                HMACSignature.getHeaderWithoutApiKey(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get assets", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun ohlc(): String? {
        val restTemplate = RestTemplate()
        val builder = UriComponentsBuilder.fromHttpUrl("$url/OHLC")
            .queryParam("symbol", "${Asset.ETH.name}/${Asset.USD.name}")
            .queryParam("interval", "1h")
        println(builder.toUriString())
        try {
            val response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                HMACSignature.getHeaderWithoutApiKey(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get OHLC", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun orderBook(): String? {
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                "$url/orderbook",
                HttpMethod.GET,
                HMACSignature.getHeaderWithoutApiKey(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get OHLC", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun summary(): String? {
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                "$url/summary",
                HttpMethod.GET,
                HMACSignature.getHeaderWithoutApiKey(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get OHLC", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun ticker(): String? {
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(
                "$url/ticker",
                HttpMethod.GET,
                HMACSignature.getHeaderWithoutApiKey(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get OHLC", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }

    fun trades(): String? {
        val restTemplate = RestTemplate()
        val builder = UriComponentsBuilder.fromHttpUrl("$url/trades")
            .queryParam("symbol", "${Asset.ETH.name}/${Asset.USD.name}")
        try {
            val response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                HMACSignature.getHeaderWithoutApiKey(),
                String::class.java
            )
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response.body
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get OHLC", e)
            throw CurrencyException(e.statusCode, e.message.toString())
        }
    }
}
