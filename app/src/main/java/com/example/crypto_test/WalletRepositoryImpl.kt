package com.example.crypto_test

import android.content.Context
import com.example.crypto_test.data.Currency
import com.example.crypto_test.data.ExchangeRate
import com.example.crypto_test.data.WalletBalance
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WalletRepositoryImpl(private val context: Context) : WalletRepository {
    private val gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private fun readJsonFromAssets(fileName: String): String {
        return context.assets.open("json/$fileName").bufferedReader().use { it.readText() }
    }

    override suspend fun getSupportedCurrencies(): Flow<List<Currency>> = flow {
        val json = readJsonFromAssets("currencies.json")
        val jsonObject = gson.fromJson(json, JsonObject::class.java)
        val currenciesArray = jsonObject.getAsJsonArray("currencies")
        val currencies = currenciesArray.map { currency ->
            val currencyObj = currency.asJsonObject
            Currency(
                code = currencyObj.get("code").asString,
                name = currencyObj.get("name").asString,
                symbol = currencyObj.get("symbol").asString,
                tokenDecimal = currencyObj.get("token_decimal").asInt,
                contractAddress = currencyObj.get("contract_address")?.asString ?: "",
                isErc20 = currencyObj.get("is_erc20").asBoolean,
                colorfulImageUrl = currencyObj.get("colorful_image_url").asString,
                grayImageUrl = currencyObj.get("gray_image_url").asString
            )
        }
        emit(currencies)
    }

    override suspend fun getExchangeRates(): Flow<List<ExchangeRate>> = flow {
        val json = readJsonFromAssets("live-rates.json")
        val jsonObject = gson.fromJson(json, JsonObject::class.java)
        val tiersArray = jsonObject.getAsJsonArray("tiers")
        val rates = tiersArray.map { tier ->
            val tierObj = tier.asJsonObject
            val rateObj = tierObj.getAsJsonArray("rates").first().asJsonObject
            ExchangeRate(
                fromCurrency = tierObj.get("from_currency").asString,
                toCurrency = tierObj.get("to_currency").asString,
                rate = rateObj.get("rate").asDouble,
                timeStamp = tierObj.get("time_stamp").asLong
            )
        }
        emit(rates)
    }

    override suspend fun getWalletBalances(): Flow<List<WalletBalance>> = flow {
        val json = readJsonFromAssets("wallet-balance.json")
        val jsonObject = gson.fromJson(json, JsonObject::class.java)
        val walletArray = jsonObject.getAsJsonArray("wallet")
        val balances = walletArray.map { balance ->
            val balanceObj = balance.asJsonObject
            WalletBalance(
                currency = balanceObj.get("currency").asString,
                amount = balanceObj.get("amount").asDouble
            )
        }
        emit(balances)
    }
}