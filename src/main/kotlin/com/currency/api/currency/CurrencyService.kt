package com.currency.api.currency

import com.currency.api.dto.AccountCurrency
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
    @Value("\${currency.url}")
    private val url: String,

    @Autowired
    private val hmacSerive: HMACSignature
) {
    private val LOG = LoggerFactory.getLogger(javaClass)

    fun getAccountInfo(): AccountCurrency? {
        val builder = UriComponentsBuilder.fromHttpUrl("$url/api/v1/account")
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
}
