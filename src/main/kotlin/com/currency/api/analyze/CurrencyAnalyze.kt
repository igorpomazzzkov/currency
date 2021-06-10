package com.currency.api.analyze

import com.currency.api.dto.Asset
import com.currency.api.dto.MyCurrency
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.io.FileOutputStream
import java.util.*


@Service
class MyCurrencyService {
    @Cacheable(value = ["MyCurrency"], key = "#asset.name()")
    fun getCurrency(asset: Asset): MyCurrency {
        val stream = this.javaClass.classLoader.getResourceAsStream("currency.properties")
        val properties = Properties()
        properties.load(stream)
        val sell = properties.getProperty("${asset.name}.sell")
        val buy = properties.getProperty("${asset.name}.buy")
        return MyCurrency(
            sell = sell.toDouble(),
            buy = buy.toDouble()
        )
    }

    @CacheEvict(value = ["MyCurrency"], allEntries = true)
    fun setCurrency(asset: Asset, currency: MyCurrency): MyCurrency {
        val stream = this.javaClass.classLoader.getResourceAsStream("currency.properties")
        val properties = Properties()
        properties.load(stream)
        properties.setProperty("${asset.name}.sell", currency.sell.toString())
        properties.setProperty("${asset.name}.buy", currency.buy.toString())
        properties.store(FileOutputStream(this.javaClass.classLoader.getResource("currency.properties").path), null)
        return getCurrency(asset)
    }
}
