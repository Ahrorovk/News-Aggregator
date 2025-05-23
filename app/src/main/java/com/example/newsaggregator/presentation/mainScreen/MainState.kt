package com.example.newsaggregator.presentation.mainScreen

import com.example.newsaggregator.domain.state.RssResponseState

data class MainState(
    val rssDto: RssResponseState = RssResponseState(),
    val searchState: String = "",
    val selectedTags: Set<String> = emptySet()
)
