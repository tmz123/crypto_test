package com.example.crypto_test.ui.composables

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.crypto_test.log.WalletLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {}
) {
    WalletLogger.i("NetworkImage:url=${url}")
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        bitmap = withContext(Dispatchers.IO) {
            try {
                val stream = URL(url).openStream()
                val bitmapTmp = BitmapFactory.decodeStream(stream)
                bitmapTmp?.asImageBitmap()
            } catch (e: Exception) {
                WalletLogger.exception(e)
                null
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!, contentDescription = contentDescription, modifier = modifier
        )
    } else {
        placeholder()
    }
}