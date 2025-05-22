package com.example.newsaggregator.domain

import com.example.newsaggregator.data.rss.dto.RssDto

interface RssFeedRepository {
    suspend fun getRss(): RssDto
}