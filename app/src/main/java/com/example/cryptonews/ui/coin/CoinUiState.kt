package com.example.cryptonews.ui.coin

import com.example.cryptonews.domain.model.Coin

data class CoinUiState(
    val query: String = "",
    val coins: List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)