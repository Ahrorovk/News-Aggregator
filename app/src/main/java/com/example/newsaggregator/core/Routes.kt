package com.example.newsaggregator.core

import com.example.newsaggregator.core.Constants.URL_ARG

sealed class Routes(val route: String) {
    object MainScreen : Routes("MainScreen")
    object NewsScreen : Routes("NewsScreen/{${URL_ARG}}")
}