package com.example.crypto_test.data

data class Currency(
    val code: String,          // 对应 JSON 中的 "code"
    val name: String,          // 对应 JSON 中的 "name"
    val symbol: String,        // 对应 JSON 中的 "symbol"
    val tokenDecimal: Int,     // 对应 JSON 中的 "token_decimal"
    val contractAddress: String = "", // 对应 JSON 中的 "contract_address"
    val isErc20: Boolean = false,    // 对应 JSON 中的 "is_erc20"
    val colorfulImageUrl: String = "",// 对应 JSON 中的 "colorful_image_url"
    val grayImageUrl: String = ""     // 对应 JSON 中的 "gray_image_url"
)
