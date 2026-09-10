package com.example.cryptonews.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TickerDto(

    val market: String,

    @SerializedName("trade_price")
    val tradePrice: Double,

    @SerializedName("opening_price")
    val openingPrice: Double,

    @SerializedName("high_price")
    val highPrice: Double,

    @SerializedName("low_price")
    val lowPrice: Double,

    @SerializedName("prev_closing_price")
    val prevClosingPrice: Double,

    @SerializedName("signed_change_price")
    val signedChangePrice: Double,

    @SerializedName("signed_change_rate")
    val signedChangeRate: Double,

    @SerializedName("acc_trade_price_24h")
    val accTradePrice24h: Double,

    @SerializedName("acc_trade_volume_24h")
    val accTradeVolume24h: Double,

    val timestamp: Long
)