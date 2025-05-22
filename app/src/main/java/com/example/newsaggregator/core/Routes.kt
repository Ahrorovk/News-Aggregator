package com.example.newsaggregator.core

sealed class Routes(val route: String) {
    object MainScreen : Routes("MainScreen")
    object NewsScreen : Routes("NewsScreen")
}