package com.example.newsaggregator

import com.example.newsaggregator.data.rss.dto.CategoryDto
import com.example.newsaggregator.data.rss.dto.ContentDto
import com.example.newsaggregator.data.rss.dto.CreditDto
import com.example.newsaggregator.data.rss.dto.ItemDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


// Пример тестируемой функции:
fun filterNewsByTags(items: List<ItemDto>, tags: Set<String>): List<ItemDto> {
    return if (tags.isEmpty()) items else
        items.filter { it.categories.any { c -> c.value in tags } }
}

// Тест:
class NewsFilterTest {
    @Test
    fun testFilterByTags() {
        val items = listOf(
            ItemDto(
                title = "A",
                categories = listOf(CategoryDto("domain1", "tag1"), CategoryDto("domain2", "tag2")),
                link = "link",
                description = "desc",
                pubDate = "",
                guid = "guid",
                contents = listOf(ContentDto("", "", "", CreditDto("", ""))),
                dcCreator = "Creator",
                dcDate = "2025-01-01"
            ),
            ItemDto(
                title = "B",
                categories = listOf(CategoryDto("domain2", "tag2")),
                link = "link",
                description = "desc",
                pubDate = "",
                guid = "guid",
                contents = listOf(ContentDto("", "", "", CreditDto("", ""))),
                dcCreator = "Creator",
                dcDate = "2025-01-01"
            ),
            ItemDto(
                title = "C",
                categories = listOf(CategoryDto("domain5", "tag5"), CategoryDto("domain5", "tag5")),
                link = "link",
                description = "desc",
                pubDate = "",
                guid = "guid",
                contents = listOf(ContentDto("", "", "", CreditDto("", ""))),
                dcCreator = "Creator",
                dcDate = "2025-01-01"
            )
        )
        val filtered = filterNewsByTags(items, setOf("tag2"))
        assertEquals(2, filtered.size)
        assertTrue(filtered.any { it.title == "A" })
        assertTrue(filtered.any { it.title == "B" })
    }
}
