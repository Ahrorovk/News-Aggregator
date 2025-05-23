package com.example.newsaggregator.presentation.mainScreen

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.core.searchFilter
import com.example.newsaggregator.presentation.components.CustomTextField
import com.example.newsaggregator.presentation.components.ErrorIndicator
import com.example.newsaggregator.presentation.components.NewsItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    LaunchedEffect(true) {
        onEvent(MainEvent.GetNews)
    }
    val allTags = remember(state.rssDto.response) {
        state.rssDto.response?.channel?.items
            ?.flatMap { it.categories }
            ?.map { it.value }
            ?.distinct()
            ?: emptyList()
    }

    val coroutineScope = rememberCoroutineScope()
    val color = MaterialTheme.colorScheme.background.toArgb()
    val context = LocalContext.current
    val refreshState = rememberSwipeRefreshState(state.rssDto.isLoading)

    Column(Modifier.fillMaxSize()) {
        CustomTextField(state.searchState, {
            onEvent(MainEvent.OnSearchStateChange(it))
        }, "Search...") {

        }
        Spacer(Modifier.padding(12.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(allTags) { tag ->
                Box(
                    Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            if (state.selectedTags.contains(tag))
                                onEvent(MainEvent.OnMinusTagChange(tag))
                            else
                                onEvent(MainEvent.OnPlusTagChange(tag))
                        }
                        .background(if (state.selectedTags.contains(tag)) Color.Blue else Color.Gray)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(tag, color = Color.White)
                }
            }
        }
        ErrorIndicator(state.rssDto.error) {
            onEvent(MainEvent.GetNews)
        }
        Spacer(Modifier.padding(12.dp))
        state.rssDto.response?.let { resp ->
            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                state = refreshState,
                onRefresh = {
                    onEvent(MainEvent.GetNews)
                }
            ) {
                LazyColumn {
                    items(resp.channel.items.filter { item ->
                        state.selectedTags.isEmpty() || item.categories.any {
                            state.selectedTags.contains(
                                it.value
                            )
                        }
                    }) { item ->
                        if (searchFilter(item, state.searchState)) {
                            NewsItem(item) { url ->
                                openInCustomTab(color, context, url)
                            }
                            Spacer(Modifier.padding(20.dp))
                        }
                    }
                }
            }
        }
    }
}

fun openInCustomTab(color: Int, context: Context, url: String) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setToolbarColor(color)
        .setShowTitle(true)
        .build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}