package com.example.crypto_test.data

data class ExchangeRate(
    val fromCurrency: String,  // 对应 JSON 中的 "from_currency"
    val toCurrency: String,    // 对应 JSON 中的 "to_currency"
    val rate: Double,          // 对应 JSON 中的 rates[0].rate
    val timeStamp: Long       // 对应 JSON 中的 "time_stamp"
)
