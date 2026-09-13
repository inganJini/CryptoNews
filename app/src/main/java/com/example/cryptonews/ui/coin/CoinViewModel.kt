package com.example.cryptonews.ui.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptonews.data.remote.dto.MarketDto
import com.example.cryptonews.data.repository.CoinRepository
import com.example.cryptonews.domain.model.Coin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class CoinViewModel(
    private val repository: CoinRepository
) : ViewModel() {

    private val query =
        MutableStateFlow("")

    private val _uiState =
        MutableStateFlow(
            CoinUiState()
        )

    val uiState: StateFlow<CoinUiState> =
        _uiState.asStateFlow()

    private var markets: List<MarketDto> = emptyList()

    init {

        loadMarkets()

        observeQuery()
    }


    fun onQueryChanged(
        value: String
    ) {
        query.value = value

        _uiState.update {
            it.copy(
                query = value
            )
        }
    }


    private fun loadMarkets() {
        viewModelScope.launch {
            runCatching {
                repository.getMarkets()
            }.onSuccess {
                markets = it
            }.onFailure {
                _uiState.update { state ->
                    state.copy(
                        errorMessage = it.message
                    )
                }
            }
        }
    }


    @OptIn(
        FlowPreview::class, ExperimentalCoroutinesApi::class
    )
    private fun observeQuery() {

        query.debounce(300)
            .map {
                it.trim()
            }
            .distinctUntilChanged()
            .mapLatest { keyword ->
                if (keyword.isBlank()) {
                    emptyList()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }
                    search(
                        keyword
                    )
                }
            }
            .catch {throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message
                    )
                }
            }
            .onEach { coins ->
                _uiState.update {
                    it.copy(
                        coins = coins,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun search(
        keyword: String
    ): List<Coin> {

        val result =
            markets.filter {
                it.koreanName .contains(
                                keyword,
                                true
                            )
                || it.englishName .contains(
                                keyword,
                                true
                            )
                || it.market .contains(
                                keyword,
                                true
                            )
            }

        return repository
            .getTickers(result)
    }

    private suspend fun searchCoin(
        query: String
    ) {

        _uiState.update {

            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        val filteredMarkets =
            markets.filter { market ->

                market.koreanName
                    .contains(
                        query,
                        ignoreCase = true
                    ) ||

                        market.englishName
                            .contains(
                                query,
                                ignoreCase = true
                            ) ||

                        market.market
                            .contains(
                                query,
                                ignoreCase = true
                            )
            }


        runCatching {

            repository.getTickers(
                filteredMarkets
            )

        }.onSuccess { coins ->

            _uiState.update {

                it.copy(
                    coins = coins,
                    isLoading = false
                )
            }

        }.onFailure { throwable ->

            _uiState.update {

                it.copy(
                    coins =
                        emptyList(),

                    isLoading =
                        false,

                    errorMessage =
                        throwable.message
                )
            }
        }
    }
}