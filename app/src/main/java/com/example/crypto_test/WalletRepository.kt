package com.example.crypto_test

import com.example.crypto_test.data.Currency
import com.example.crypto_test.data.ExchangeRate
import com.example.crypto_test.data.WalletBalance
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun getSupportedCurrencies(): Flow<List<Currency>>
    suspend fun getExchangeRates(): Flow<List<ExchangeRate>>
    suspend fun getWalletBalances(): Flow<List<WalletBalance>>
}