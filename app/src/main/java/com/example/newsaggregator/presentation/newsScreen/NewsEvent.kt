package com.example.newsaggregator.presentation.newsScreen

sealed class NewsEvent {
    data class OnUrlChange(val url: String) : NewsEvent()
}