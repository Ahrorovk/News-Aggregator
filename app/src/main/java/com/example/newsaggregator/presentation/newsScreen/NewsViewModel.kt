package com.example.newsaggregator.presentation.newsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(NewsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        NewsState()
    )

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.OnUrlChange -> {
                _state.update {
                    it.copy(
                        urlState = event.url
                    )
                }
            }

            else -> Unit
        }
    }
}