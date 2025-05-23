package com.example.newsaggregator.presentation.newsScreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.newsaggregator.presentation.webView.WebViewComponent
import com.google.accompanist.web.rememberWebViewState

@Composable
fun NewsScreen(
    state: NewsState,
    onEvent: (NewsEvent) -> Unit
) {
    val context = LocalContext.current
    val webViewState = rememberWebViewState(url = state.urlState)
    WebViewComponent(webViewState,MaterialTheme.colorScheme.background)
}