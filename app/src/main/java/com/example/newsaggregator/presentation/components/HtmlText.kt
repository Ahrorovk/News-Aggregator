package com.example.newsaggregator.presentation.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

@Composable
fun HtmlText(html: String, goToUrl: (String) -> Unit) {
    val doc: Document = Jsoup.parse(html)
    val builder = buildAnnotatedString {
        fun parseNode(node: Node) {
            when (node) {
                is TextNode -> append(node.text())
                is org.jsoup.nodes.Element -> {
                    when (node.tagName()) {
                        "a" -> {
                            val url = node.attr("href")

                            pushStringAnnotation(tag = "URL", annotation = url)
                            withStyle(
                                SpanStyle(
                                    color = Color(0xFF1565C0),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(node.text())
                            }
                            pop()
                        }

                        "p" -> {
                            if (length > 0) append("\n\n")
                            node.childNodes().forEach { parseNode(it) }
                        }

                        else -> node.childNodes().forEach { parseNode(it) }
                    }
                }
            }
        }
        doc.body().childNodes().forEach { parseNode(it) }
    }

    ClickableText(
        text = builder,
        onClick = { offset ->
            builder.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let {
                    goToUrl(it.item)
                }
        },
        style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
    )
}
