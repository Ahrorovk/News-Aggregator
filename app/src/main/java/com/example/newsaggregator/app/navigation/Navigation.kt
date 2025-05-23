package com.example.newsaggregator.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsaggregator.core.Constants.URL_ARG
import com.example.newsaggregator.core.Routes
import com.example.newsaggregator.presentation.components.EnterAnimation
import com.example.newsaggregator.presentation.mainScreen.MainEvent
import com.example.newsaggregator.presentation.mainScreen.MainScreen
import com.example.newsaggregator.presentation.mainScreen.MainViewModel
import com.example.newsaggregator.presentation.newsScreen.NewsEvent
import com.example.newsaggregator.presentation.newsScreen.NewsScreen
import com.example.newsaggregator.presentation.newsScreen.NewsViewModel
import java.net.URLDecoder
import java.net.URLEncoder

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
                        is MainEvent.GoToNews -> {
                            navController.navigate(
                                Routes.NewsScreen.route.replace(
                                    "{${URL_ARG}}",
                                    URLEncoder.encode(event.url, "UTF-8")
                                )
                            )
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            }
            composable(
                Routes.NewsScreen.route, arguments = listOf(
                    navArgument(URL_ARG) {
                        type = NavType.StringType
                    }
                )) { navBackStackEntry ->
                val url = URLDecoder.decode(
                    navBackStackEntry.arguments?.getString(URL_ARG) ?: "",
                    "UTF-8"
                )
                val viewModel = hiltViewModel<NewsViewModel>()
                LaunchedEffect(true) {
                    viewModel.onEvent(NewsEvent.OnUrlChange(url))
                }
                val state = viewModel.state.collectAsState()
                EnterAnimation {
                    NewsScreen(state.value) { event ->
                        when (event) {
                            else -> viewModel.onEvent(event)
                        }
                    }
                }
            }
        }
    }
}