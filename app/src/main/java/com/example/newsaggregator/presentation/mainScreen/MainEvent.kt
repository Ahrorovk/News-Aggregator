package com.example.newsaggregator.presentation.mainScreen

sealed class MainEvent {
    data class OnSearchStateChange(val state: String) : MainEvent()
    data class OnPlusTagChange(val state: String) : MainEvent()
    data class OnMinusTagChange(val state: String) : MainEvent()
    data class GoToNews(val url: String) : MainEvent()
    object GetNews : MainEvent()
    object ClearNews : MainEvent()

}