package com.currency.api.exception

import java.util.*

data class BotException(
    val status: Int,
    val message: String,
    val time: Date
)

