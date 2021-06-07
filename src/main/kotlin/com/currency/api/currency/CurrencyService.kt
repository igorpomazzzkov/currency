package com.currency.api.currency

import com.currency.api.exception.CurrencyException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.sql.Timestamp
import java.util.*


@Service
class CurrencyService(
    @Value("\${currency.url}")
    private val url: String,

    @Value("\${currency.api.key}")
    private val apiKey: String,

    @Value("\${currency.secret.key}")
    private val secretKey: String
) {
    private val LOG = LoggerFactory.getLogger(javaClass)

    fun getAccountInfo(): ResponseEntity<String.Companion> {
        val builder = UriComponentsBuilder.fromHttpUrl("$url/api/v1/account")
            .queryParam("msisdn", Timestamp(Date().time))
        LOG.info("currency.com get account info")
        val restTemplate = RestTemplate()
        try {
            val response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHeader(), String.javaClass)
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                LOG.error("error from currency.com, status: ${response.statusCode}, response: $response")
            }
            return response
        } catch (e: HttpStatusCodeException) {
            LOG.error("exception on get account info", e)
            throw CurrencyException(HttpStatus.BAD_REQUEST, "Exception on get account info in currency.com")
        }
    }

    private fun getHeader(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("X-MBX-APIKEY", apiKey)
        return HttpEntity<String>(headers)
    }
}
