package com.example.newsaggregator.presentation.mainScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        state.rssDto?.let { resp ->
            LazyColumn {
                items(resp.channel.items) { item ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x7A818181)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(item.title)
                        Text(item.description)
                        val painter = rememberAsyncImagePainter(item.contents.last())
                        if (painter.state !is Success) {
                            CircularProgressIndicator()
                        }
                        if (painter.state is AsyncImagePainter.State.Error) {
                            Toast.makeText(
                                context,
                                painter.state.painter.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        Image(
                            painter = rememberAsyncImagePainter(
                                item.contents.last().url.substringBeforeLast(
                                    '?'
                                )
                            ),
                            contentDescription = "content.type",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(item.dcCreator)
                    }
                    Spacer(Modifier.padding(20.dp))

                }
            }
        }
    }
}