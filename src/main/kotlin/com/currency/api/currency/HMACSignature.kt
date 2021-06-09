package com.currency.api.currency

import org.apache.commons.codec.binary.Hex
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class HMACSignature(
    @Value("\${currency.api.key}")
    private val apiKey: String,

    @Value("\${currency.secret.key}")
    private val secretKey: String
) {
    fun getHeader(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("X-MBX-APIKEY", this.apiKey)
        return HttpEntity<String>(headers)
    }

    fun createSignature(data: String): String {
        val query = data.replace("/", "%2F")
        val sha256HMac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(this.secretKey.toByteArray(), "HmacSHA256")
        sha256HMac.init(secretKey)
        return Hex.encodeHexString(sha256HMac.doFinal(query.toByteArray()))
    }

    companion object {
        fun getHeaderWithoutApiKey(): HttpEntity<String> {
            val headers = HttpHeaders()
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
            return HttpEntity<String>(headers)
        }
    }
}
