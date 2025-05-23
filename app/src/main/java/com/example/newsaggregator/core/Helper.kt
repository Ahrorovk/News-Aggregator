package com.example.newsaggregator.core

import com.example.newsaggregator.data.rss.dto.ItemDto

fun searchFilter(item: ItemDto, searchState: String): Boolean {
    return item.title.toLowerCase().contains(searchState.toLowerCase()) ||
            item.description.toLowerCase().contains(searchState.toLowerCase()) ||
            item.dcCreator.toLowerCase().contains(searchState.toLowerCase())
}