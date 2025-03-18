package com.example.crypto_test.log

import android.util.Log

object WalletLogger {
    private fun getTAG(): String {
        return "WalletLogger"
    }

    fun i(msg: String) {
        Log.i(getTAG(), msg)
    }

    fun e(msg: String) {
        Log.e(getTAG(), msg)
    }

    fun exception(t: Throwable) {
        val stackTrace = Log.getStackTraceString(t)
        val tag = getTAG()
        Log.e(tag, stackTrace)
    }
}