package com.example.crypto_test.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crypto_test.R
import com.example.crypto_test.WalletViewModel
import com.example.crypto_test.WalletViewModelFactory
import com.example.crypto_test.data.WalletItem
import com.example.crypto_test.data.WalletUIState
import com.example.crypto_test.log.WalletLogger

@Composable
@Preview(showBackground = true)
fun WalletScreenPreview() {
    WalletScreen()
}

@Composable
fun WalletScreen(exit: () -> Unit = {}, toast: (String) -> Unit = {}) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val viewModel: WalletViewModel = viewModel(
        factory = WalletViewModelFactory(context)
    )

    val walletState by viewModel.walletState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0E1121))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .height(32.dp)
                        .clickable { exit() },
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = when (walletState) {
                    is WalletUIState.Success -> buildAnnotatedString {
                        withStyle(style = androidx.compose.ui.text.SpanStyle(color = Color.Gray)) {
                            append("$")
                        }
                        withStyle(style = androidx.compose.ui.text.SpanStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )) {
                            val balance = (walletState as WalletUIState.Success).totalUsdBalance
                            // 处理负数情况
                            if (balance < 0) {
                                append("0.00")
                            } else {
                                append("%.2f".format(balance))
                            }
                        }
                        withStyle(style = androidx.compose.ui.text.SpanStyle(color = Color.Gray)) {
                            append(" USD")
                        }
                    }
                    is WalletUIState.Loading -> buildAnnotatedString {
                        append("Loading...")
                    }
                    is WalletUIState.Error -> buildAnnotatedString {
                        withStyle(style = androidx.compose.ui.text.SpanStyle(color = Color.Red)) {
                            append("Error loading balance")
                            toast.invoke("Sorry, there was an error.")
                        }
                    }
                },
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(icon = Icons.AutoMirrored.Filled.Send,
                    text = "Send",
                    onClick = {
                        WalletLogger.i("Send Click")
                    })
                ActionButton(icon = Icons.Default.Call,
                    text = "Receive",
                    onClick = {
                        WalletLogger.i("Receive Click")
                    })
            }
        }

        // Currency List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
        ) {
            when (walletState) {
                is WalletUIState.Success -> {
                    val items = (walletState as WalletUIState.Success).walletItems
                    if (items.isEmpty()) {
                        // 空列表处理
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No currencies available",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items) { item ->
                                CurrencyListItem(item = item)
                            }
                        }
                    }
                }
                is WalletUIState.Loading -> {
                    // 加载状态显示
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is WalletUIState.Error -> {
                    // 错误状态显示
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Failed to load wallet data",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.refreshWalletData()
                                WalletLogger.i("Retry loading wallet data")
                            }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    icon: ImageVector, text: String, onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF4287F5), CircleShape)
                .clickable(onClick = onClick), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon, contentDescription = text, tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text, color = Color.White, style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun CurrencyListItem(item: WalletItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffeeeee4)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                NetworkImage(
                    url = item.currency.colorfulImageUrl,
                    contentDescription = item.currency.name,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    placeholder = {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = item.currency.name.ifEmpty { "Unknown" },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${item.balance.coerceAtLeast(0.0)} ${item.currency.code}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$${("%.2f".format(item.usdValue.coerceAtLeast(0.0)))}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}