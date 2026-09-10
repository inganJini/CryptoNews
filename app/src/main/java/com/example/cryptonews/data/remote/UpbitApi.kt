package com.example.cryptonews.data.remote

import com.example.cryptonews.data.remote.dto.MarketDto
import com.example.cryptonews.data.remote.dto.TickerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApi {

    @GET("v1/market/all")
    suspend fun getMarkets(
        @Query("is_details")
        isDetails: Boolean = false
    ): List<MarketDto>


    @GET("v1/ticker")
    suspend fun getTickers(
        @Query("markets")
        markets: String
    ): List<TickerDto>


    @GET("v1/ticker/all")
    suspend fun getAllTickers(
        @Query("quote_currencies")
        quoteCurrencies: String = "KRW"
    ): List<TickerDto>
}