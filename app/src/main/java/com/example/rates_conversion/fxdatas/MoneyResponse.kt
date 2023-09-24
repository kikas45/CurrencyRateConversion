package com.example.rates_conversion.fxdatas

data class MoneyResponse(
    val base: String,
    val date: String,
    val rates: Money_Rates,
    val success: Boolean,
    val timestamp: Int
)