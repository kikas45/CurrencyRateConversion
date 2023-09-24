package com.example.rates_conversion.fxdatas

data class MoneyResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)