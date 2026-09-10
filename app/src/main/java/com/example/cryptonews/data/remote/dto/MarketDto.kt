package com.example.cryptonews.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MarketDto (
    val market: String,

    @SerializedName("korean_name")
    val koreanName: String,

    @SerializedName("english_name")
    val englishName: String
)