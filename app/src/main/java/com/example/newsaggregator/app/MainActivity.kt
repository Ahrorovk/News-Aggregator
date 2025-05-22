package com.example.newsaggregator.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsaggregator.app.navigation.Navigation
import com.example.newsaggregator.app.navigation.NavigationViewModel
import com.example.newsaggregator.app.ui.theme.NewsAggregatorTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAggregatorTheme {
                val viewModel = hiltViewModel<NavigationViewModel>()
                val state = viewModel.state.collectAsState()
                Navigation(state.value) { event ->
                    when (event) {
                        else -> viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}
