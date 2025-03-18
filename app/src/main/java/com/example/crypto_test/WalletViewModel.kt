package com.example.crypto_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_test.data.Currency
import com.example.crypto_test.data.ExchangeRate
import com.example.crypto_test.data.WalletBalance
import com.example.crypto_test.data.WalletItem
import com.example.crypto_test.data.WalletUIState
import com.example.crypto_test.log.WalletLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class WalletViewModel(
    private val repository: WalletRepository
) : ViewModel() {

    private val _walletState = MutableStateFlow<WalletUIState>(WalletUIState.Loading)
    val walletState: StateFlow<WalletUIState> = _walletState.asStateFlow()

    init {
        loadWalletData()
    }

    fun refreshWalletData() {
        loadWalletData()
    }

    private fun loadWalletData() {
        viewModelScope.launch {
            try {
                combine(
                    repository.getSupportedCurrencies(),
                    repository.getExchangeRates(),
                    repository.getWalletBalances()
                ) { currencies, rates, balances ->
                    calculateWalletState(currencies, rates, balances)
                }.collect { state ->
                    _walletState.value = state
                }
            } catch (e: Exception) {
                WalletLogger.exception(e)
                _walletState.value = WalletUIState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun calculateWalletState(
        currencies: List<Currency>, rates: List<ExchangeRate>, balances: List<WalletBalance>
    ): WalletUIState {
        val walletItems = currencies.map { currency ->
            val balance = balances.find { it.currency == currency.code }?.amount ?: 0.0
            val rate = rates.find { it.fromCurrency == currency.code }?.rate ?: 0.0
            val usdValue = balance * rate

            WalletItem(currency, balance, usdValue)
        }

        val totalUsdBalance = walletItems.sumOf { it.usdValue }
        return WalletUIState.Success(totalUsdBalance, walletItems)
    }
}