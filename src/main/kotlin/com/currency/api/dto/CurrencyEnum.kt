package com.currency.api.dto

// статус заявок
enum class OrderStatus {
    NEW, FILLED, CANCELED, REJECTED
}

// тип заявок
enum class OrderType {
    LIMIT, MARKET, STOP
}

// направление заявок
enum class OrderSide {
    BUY, SELL
}

// срок действий
enum class TimeInForce {
    GTC, IOC, FOK
}

enum class Asset {
    USD, ETH, BYN, BTC, EUR, GBR, RUB, PLAYTIKA
}
