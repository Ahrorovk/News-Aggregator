package com.example.newsaggregator.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.core.Routes
import com.example.newsaggregator.presentation.mainScreen.MainScreen
import com.example.newsaggregator.presentation.mainScreen.MainViewModel
import com.example.newsaggregator.presentation.mainScreen.NewsViewModel
import com.example.newsaggregator.presentation.newsScreen.NewsScreen

@Composable
fun Navigation(
    state: NavigationState,
    onEvent: (NavigationEvent) -> Unit
) {

    val navController = rememberNavController()

    Scaffold(Modifier.fillMaxSize()) { it_ ->
        NavHost(
            navController,
            Routes.MainScreen.route,
            modifier = Modifier.padding(it_)
        ) {

            composable(Routes.MainScreen.route) {
                val viewModel = hiltViewModel<MainViewModel>()
                val state = viewModel.state.collectAsState()
                MainScreen(state.value) { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }
            }
            composable(Routes.NewsScreen.route) {
                val viewModel = hiltViewModel<NewsViewModel>()
                val state = viewModel.state.collectAsState()
                NewsScreen(state.value) { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}