package com.example.cryptonews.data.repository

import com.example.cryptonews.data.remote.UpbitApi
import com.example.cryptonews.data.remote.dto.MarketDto
import com.example.cryptonews.domain.model.Coin

class CoinRepository(
    private val api: UpbitApi
) {

    suspend fun getMarkets(): List<MarketDto> {

        return api.getMarkets()
            .filter {
                it.market.startsWith("KRW-")
            }
    }


    suspend fun getTickers(
        markets: List<MarketDto>
    ): List<Coin> {

        if (markets.isEmpty()) {
            return emptyList()
        }

        val marketCodes =
            markets.joinToString(",") {
                it.market
            }

        val tickers =
            api.getTickers(marketCodes)

        val marketMap =
            markets.associateBy {
                it.market
            }

        return tickers.mapNotNull { ticker ->

            val market =
                marketMap[ticker.market]
                    ?: return@mapNotNull null

            Coin(
                market = ticker.market,
                koreanName = market.koreanName,
                englishName = market.englishName,
                price = ticker.tradePrice,
                changePrice = ticker.signedChangePrice,
                changeRate = ticker.signedChangeRate
            )
        }
    }
}