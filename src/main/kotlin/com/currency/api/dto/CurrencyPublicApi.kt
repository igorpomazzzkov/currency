package com.currency.api.dto

data class OHLCResult(
    val data: List<String>,
    val last: Long
)

data class OHLCResponse(
    val result: OHLCResult,
    val error: List<String>
)
