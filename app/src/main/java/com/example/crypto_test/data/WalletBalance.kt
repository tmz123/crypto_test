package com.example.crypto_test.data

data class WalletBalance(
    val currency: String,  // 对应 JSON 中的 "currency"
    val amount: Double     // 对应 JSON 中的 "amount"
)
