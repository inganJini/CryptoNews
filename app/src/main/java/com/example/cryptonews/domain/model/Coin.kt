package com.example.cryptonews.domain.model

data class Coin(
    val market: String,
    val koreanName: String,
    val englishName: String,
    val price: Double,
    val changePrice: Double,
    val changeRate: Double
)