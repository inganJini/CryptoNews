package com.example.cryptonews.ui.coin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptonews.domain.model.Coin

@Composable
fun CoinScreen(viewModel: CoinViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CoinContent(
        uiState = uiState,
        onQueryChanged = viewModel::onQueryChanged
    )
}

@Composable
fun CoinContent(
    uiState: CoinUiState,
    onQueryChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.query,
            onValueChange = onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    "가상자산 검색"
                )
            },
            placeholder = {
                Text(
                    "비트코인, BTC..."
                )
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        when {
            uiState.isLoading -> {
                CircularProgressindicator()
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage ?: "오류가 발생했습니다."
                )
            }

            else -> {
                CoinList(
                    coins = uiState.coins
                )
            }
        }
    }

}

@Composable
fun CoinList(
    coins: List<Coin>
) {
    LazyColumn {
        items(
            items = coins,
            key = {
                it.
            }
        )
    }
}

@Composable
fun CoinItem(
    coin: Coin
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = coin.koreanName,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = coin.market,
                style = MaterialTheme.typography.bodySmall
            )
        }


        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "%,.0f원".format(coin.price)
            )

            Text(
                text = "%.2f%%".format(coin.changeRate * 100)
            )
        }
    }
}