package com.example.crypto_test.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.crypto_test.ui.composables.WalletScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.navigationBarColor = Color.Black.toArgb()
        window.statusBarColor = Color.Black.toArgb()

        val exit = fun() {
            finish()
        }

        val toast = fun(message: String) {
            Toast.makeText(baseContext,message,Toast.LENGTH_SHORT).show()
        }

        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF151620)) {
                WalletScreen(exit, toast)
            }
        }
}
}