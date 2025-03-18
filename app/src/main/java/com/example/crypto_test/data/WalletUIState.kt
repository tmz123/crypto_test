package com.example.crypto_test.data

sealed class WalletUIState {
    data object Loading : WalletUIState()
    data class Success(
        val totalUsdBalance: Double,
        val walletItems: List<WalletItem>
    ) : WalletUIState()
    data class Error(val message: String) : WalletUIState()
}

data class WalletItem(
    val currency: Currency,
    val balance: Double,
    val usdValue: Double
)