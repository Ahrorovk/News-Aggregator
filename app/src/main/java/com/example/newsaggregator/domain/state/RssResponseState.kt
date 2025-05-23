package com.example.newsaggregator.domain.state

import com.example.newsaggregator.data.rss.dto.RssDto

data class RssResponseState(
    val isLoading: Boolean = false,
    val response: RssDto? = null,
    val error: String = ""
)
