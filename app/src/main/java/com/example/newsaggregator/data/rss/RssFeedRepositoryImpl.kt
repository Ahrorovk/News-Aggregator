package com.example.newsaggregator.data.rss

import com.example.newsaggregator.data.rss.dto.RssDto
import com.example.newsaggregator.domain.RssFeedRepository
import javax.inject.Inject

class RssFeedRepositoryImpl @Inject constructor(
    private val rssFeed: RssFeed
) : RssFeedRepository {
    override suspend fun getRss(): RssDto = rssFeed.getRss()
}