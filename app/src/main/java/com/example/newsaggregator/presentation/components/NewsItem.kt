package com.example.newsaggregator.presentation.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.newsaggregator.data.rss.dto.ItemDto

@Composable
fun NewsItem(item: ItemDto, onClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x7A818181))
            .clickable {
                onClick(item.guid)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(item.contents.last().url)
        if (painter.state is AsyncImagePainter.State.Error) {
            Toast.makeText(
                context,
                painter.state.painter.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
        Box(contentAlignment = Alignment.TopEnd) {
            AsyncImage(
                model = item.contents.last().url,
                contentDescription = "type",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(340.dp)
            )
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(40.dp)
                    .padding(top=6.dp, end = 6.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, item.title)
                            putExtra(Intent.EXTRA_TEXT, item.guid)
                        }
                        context.startActivity(Intent.createChooser(intent, "Share news"))
                    }
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(item.title)
            Divider(Modifier.fillMaxWidth())
            Spacer(Modifier.padding(6.dp))
            HtmlText(item.description) { url ->
                onClick(url)
            }
            Spacer(Modifier.padding(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.dcCreator)
                Text(item.dcDate)
            }
            Spacer(Modifier.padding(6.dp))
            Text("Categories:")
            Spacer(Modifier.padding(3.dp))
            LazyRow {
                items(item.categories) { category ->
                    CategoryItem(category)
                    Spacer(Modifier.padding(3.dp))
                }
            }
        }
    }
}