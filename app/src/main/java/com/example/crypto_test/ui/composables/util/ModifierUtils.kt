package com.example.crypto_test.ui.composables.util

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * 去掉涟漪效果的点击
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

/**
 * 阻断点击事件透传
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickBlock(): Modifier = clickableNoRipple {
    //nothing
}