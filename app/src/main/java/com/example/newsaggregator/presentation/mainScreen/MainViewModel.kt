package com.example.newsaggregator.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.core.Resource
import com.example.newsaggregator.domain.state.RssResponseState
import com.example.newsaggregator.domain.use_case.GetRssUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRssUseCase: GetRssUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MainState()
    )

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.ClearNews -> {

            }

            MainEvent.GetNews -> {
                getRss()
            }

            is MainEvent.OnSearchStateChange -> {
                _state.update {
                    it.copy(
                        searchState = event.state
                    )
                }
            }
            is MainEvent.OnPlusTagChange->{
                _state.update {
                    it.copy(
                        selectedTags = _state.value.selectedTags.plus(event.state)
                    )
                }
            }
            is MainEvent.OnMinusTagChange->{
                _state.update {
                    it.copy(
                        selectedTags = _state.value.selectedTags.minus(event.state)
                    )
                }
            }

            else -> Unit
        }
    }

    private fun getRss() {
        getRssUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Error<*> -> {
                    _state.update {
                        it.copy(rssDto = RssResponseState(error = result.message.toString()))
                    }
                    Log.e("TAG", "Error->" + result.message.toString())
                }

                is Resource.Loading<*> -> {
                    _state.update {
                        it.copy(rssDto = RssResponseState(isLoading = true))
                    }
                }

                is Resource.Success<*> -> {
                    val response = result.data
                    _state.update {
                        it.copy(
                            rssDto = RssResponseState(response = response)
                        )
                    }
                    Log.e("TAG", "Success-> ${_state.value.rssDto.response}")
                }
            }
        }.launchIn(viewModelScope + Dispatchers.IO)
    }
}