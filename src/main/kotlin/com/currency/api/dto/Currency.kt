package com.currency.api.dto

data class AccountCurrency(
    val makerCommission: Double,
    val takerCommission: Double,
    val buyerCommission: Double,
    val sellerCommission: Double,
    val canTrade: Boolean,
    val canWithdraw: Boolean,
    val canDeposit: Boolean,
    val updateTime: Long,
    val affiliateId: String,
    val userId: Long,
    val balances: List<AccountCurrencyBalance>
)

data class AccountCurrencyBalance(
    val accountId: String,
    val collateralCurrency: Boolean,
    val asset: String,
    val free: Long,
    val locked: Long,
    val default: Boolean
)